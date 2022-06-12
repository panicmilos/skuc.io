import { FC, useEffect, useState } from "react";
import { useLocationsService, Location, Form, FormSelectOptionInput, FormTextInput, Button } from "../../imports";
import * as Yup from 'yup';
import { createUseStyles } from "react-jss";

type Props = {
  groupId: string,
  submit: (r: any) => void
}

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const SelectReportForm: FC<Props> = ({ groupId, submit }) => {

  const [locationsService] = useLocationsService(groupId);

  const [locationOptions, setLocationOptions] = useState<any>([]);

  useEffect(() => {
    locationsService.fetchAll().then(locations => {
      const options = locations?.map((location: Location) => ({
        label: location?.name,
        value: location?.id
      })) ?? [];

      setLocationOptions(options);
    });
  }, []);

  const schema = Yup.object().shape({
    locationId: Yup.string().required(() => ({ locationId: "Location must be provided." })),
    paramName: Yup.string().required(() => ({ paramName: "Param Name must be provided." })),
  });

  const classes = useStyles();

  return (
    <>
      <Form
        schema={schema}
        onSubmit={values => submit(values)}
      >

        <FormSelectOptionInput
          label='Location'
          name='locationId'
          options={locationOptions}
        />

        <FormTextInput
          label='Param Name'
          name='paramName'
        />   

        <div className={classes.submitButton} >
          <Button type="submit">Submit</Button>
        </div>
        
      </Form>
    </>
  );


}