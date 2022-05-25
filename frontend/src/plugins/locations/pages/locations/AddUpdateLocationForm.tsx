import { FC, useContext, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { ALPHANUMERIC_REGEX, Button, extractErrorMessage, Form, FormTextInput, NotificationService } from "../../imports";
import { CreateLocationRequest, Location, UpdateLocationRequest } from "../../models"
import { useLocationsService } from "../../services";
import { LocationsContext } from "./Locations";
import * as Yup from 'yup';
import { ADD_LOCATION, UPDATE_LOCATION } from "./LocationActions";
import { AxiosError } from "axios";

type Props = {
  groupId: string,
  existingLocation?: Location,
  isEdit?: boolean
}

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const AddUpdateLocationForm: FC<Props> = ({ groupId, existingLocation = undefined, isEdit = false }) => {
  
  const queryClient = useQueryClient();
  const [locationsService] = useLocationsService(groupId);
  const [notificationService] = useState(new NotificationService());

  const { setResult } = useContext(LocationsContext);

  const schema = Yup.object().shape({
    name: Yup.string()
      .required(() => ({ name: "Name must be provided." })) 
      .matches(ALPHANUMERIC_REGEX, () => ({name: "Must be a valid name."})),
    lng: Yup.number()
      .required(() => ({ name: "Lng must be provided." })),
    lat: Yup.number()
      .required(() => ({ name: "Lat must be provided." }))
  });

  const addLocationMutation = useMutation((newLocation: CreateLocationRequest) => locationsService.add(newLocation), {
    onSuccess: () => {
      queryClient.invalidateQueries(locationsService.ID);
      notificationService.success('You have successfully created new location.');

      setResult({ status: 'OK', action: ADD_LOCATION });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const addLocation = (newLocation: CreateLocationRequest) => addLocationMutation.mutate(newLocation)

  const updateLocationMutation = useMutation((updateLocation: UpdateLocationRequest) => locationsService.update(existingLocation?.id ?? '', updateLocation), {
    onSuccess: () => {
      queryClient.invalidateQueries(locationsService.ID);
      notificationService.success('You have successfully updated a location.');

      setResult({ status: 'OK', action: UPDATE_LOCATION });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const updateLocation = (updateLocation: UpdateLocationRequest) => updateLocationMutation.mutate(updateLocation);

  const classes = useStyles();

  return (
    <>
      <Form
        initialValue={ existingLocation || {} }
        schema={schema}
        onSubmit={!isEdit ? addLocation : updateLocation}
      >
        <FormTextInput label="Name" name="name" />
        <FormTextInput label="Longitude" name="lng"/>
        <FormTextInput label="Latitude" name="lat"/>

        <div className={classes.submitButton} >
          <Button type="submit">Submit</Button>
        </div>
        
      </Form>
    </>
  );
}