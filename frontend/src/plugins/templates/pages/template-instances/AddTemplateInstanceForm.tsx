import { AxiosError } from "axios";
import { FC, useContext, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { extractErrorMessage, NotificationService, useLocationsService, Location, Form, FormSelectOptionInput, Button, FormTextAreaInput, FormTextInput } from "../../imports";
import { CreateTemplateInstance } from "../../models";
import { useTemplateInstanceService, useTemplatesService } from "../../services";
import { TemplateInstancesContext } from "./TemplateInstances";
import { ADD_TEMPLATE_INSTANCE } from "./TemplateInstancesActions";
import * as Yup from 'yup';

type Props = {
  groupId: string,
  templateId: string
}

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const AddTemplateInstanceForm: FC<Props> = ({ groupId, templateId }) => {

  const queryClient = useQueryClient();
  const [templatesService] = useTemplatesService(groupId);
  const [locationsService] = useLocationsService(groupId);
  const [templateInstancesService] = useTemplateInstanceService(groupId, templateId);
  const [notificationService] = useState(new NotificationService());

  const [locationOptions, setLocationOptions] = useState<any>([]);
  const [schema, setSchema] = useState<any>({});

  const { setResult } = useContext(TemplateInstancesContext);
  
  useEffect(() => {
    locationsService.fetchAll().then(locations => {
      const options = locations?.map((location: Location) => ({
        label: location?.name,
        value: location?.id
      })) ?? [];

      setLocationOptions(options);
    });
  }, []);
  

  const { data: template } = useQuery([], () => templatesService.fetch(templateId), {
    onSuccess: (template) => {
      const schema = Yup.object().shape({
        locationId: Yup.string()
          .required(() => ({ locationId: "Location must be provided." })),
          // ...template?.parameters.reduce((acc: any, param: string) => {
          //   acc[param] = Yup.string().required(() => ({ [param]: `${formatLabel(param)} must be provided` }) );

          //   return acc;
          // }, {}) ?? {}
      });
      setSchema(schema);
    }
  });

  const addTemplateInstanceMutation = useMutation((newTemplateInstance: CreateTemplateInstance) => templateInstancesService.add(newTemplateInstance), {
    onSuccess: () => {
      queryClient.invalidateQueries(templateInstancesService.ID);
      notificationService.success('You have successfully instantiated template.');

      setResult({ status: 'OK', action: ADD_TEMPLATE_INSTANCE });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const addTemplateInstance = (newTemplateInstance: CreateTemplateInstance) => addTemplateInstanceMutation.mutate(newTemplateInstance);

  const classes = useStyles();

  function isNumeric(n: any) {
    return !isNaN(parseFloat(n)) && isFinite(n);
  }
  
  const formatLabel = (label: string) => label[0].toUpperCase() + label.substring(1);

  return (
    <>
      <Form
        schema={schema}
        onSubmit={values => {
            const instance = {
              locationId: values.locationId,
              values: template?.parameters.map(param => {
                const value = values[param].trim();
                return isNumeric(value) ? +value : value;
              }) ?? []
            };
            
            console.log(instance);
            addTemplateInstance(instance);
          }
        }
      >

        <FormSelectOptionInput
          label='Location'
          name='locationId'
          options={locationOptions}
        />

        {
          template?.parameters.map(param => {
            return <FormTextInput label={formatLabel(param)} name={param} />
          })
        }

        <div className={classes.submitButton} >
          <Button type="submit">Submit</Button>
        </div>
        
      </Form>
    </>
  );
}
