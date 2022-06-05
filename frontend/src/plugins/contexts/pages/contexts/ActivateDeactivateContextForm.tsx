import { FC, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Location, Button, extractErrorMessage, Form, FormSelectOptionInput, NotificationService, useLocationsService } from "../../imports";
import { useContextsService } from "../../services";
import * as Yup from 'yup';
import { AxiosError } from "axios";

type Props = {
  groupId: string,
  contextId: string,
  action: string
}

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const ActivateDeactivateContextForm: FC<Props> = ({ groupId, contextId, action }) => {

  const queryClient = useQueryClient();
  const [contextsService] = useContextsService(groupId);
  const [locationsService] = useLocationsService(groupId);
  const [notificationService] = useState(new NotificationService());

  const [locationOptions, setLocationOptions] = useState<any>([]);

  const schema = Yup.object().shape({
    locationId: Yup.string()
      .required(() => ({ locationId: "Location must be provided." })) 
  });

  const { data: locations } = useQuery([locationsService], () => locationsService.fetchAll());

  useEffect(() => {
    const options = locations?.map((location: Location) => ({
      label: location?.name,
      value: location?.id
    })) ?? [];

    setLocationOptions(options);
  }, [locations]);

  const activateContextMutation = useMutation((locationId: string) => contextsService.activate(contextId, locationId), {
    onSuccess: () => {
      queryClient.invalidateQueries(contextsService.ID);
      notificationService.success('You have successfully activated context.');
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const activateContext = (locationId: string) => activateContextMutation.mutate(locationId);

  const deactivateContextMutation = useMutation((locationId: string) => contextsService.deactivate(contextId, locationId), {
    onSuccess: () => {
      queryClient.invalidateQueries(contextsService.ID);
      notificationService.success('You have successfully deactivated context.');
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const deactivateContext = (locationId: string) => deactivateContextMutation.mutate(locationId);

  const classes = useStyles();

  return (
    <>
      <Form
        schema={schema}
        onSubmit={ ({ locationId }) => action === 'activate' ? activateContext(locationId) : deactivateContext(locationId) }
      >

        <FormSelectOptionInput
          label='Location'
          name='locationId'
          options={locationOptions}
        />

        <div className={classes.submitButton} >
          <Button type="submit">Submit</Button>
        </div>
        
      </Form>
    </>
  );
}
