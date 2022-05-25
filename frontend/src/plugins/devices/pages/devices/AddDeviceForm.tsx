import { FC, useContext, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { ALPHANUMERIC_REGEX, Button, extractErrorMessage, Form, FormTextInput, NotificationService } from "../../imports";
import { useDevicesService } from "../../services";
import { DevicesContext } from "./Devices";
import * as Yup from 'yup';
import { CreateDeviceRequest } from "../../models";
import { ADD_DEVICE } from "./DeviceActions";
import { AxiosError } from "axios";

type Props = {
  groupId: string,
  locationId: string
};

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});


export const AddDeviceForm: FC<Props> = ({ groupId, locationId }) => {
  const queryClient = useQueryClient();
  const [devicesService] = useDevicesService(groupId, locationId);
  const [notificationService] = useState(new NotificationService());

  const { setResult } = useContext(DevicesContext);

  const schema = Yup.object().shape({
    name: Yup.string()
      .required(() => ({ name: "Name must be provided." })) 
      .matches(ALPHANUMERIC_REGEX, () => ({name: "Must be a valid name."})),
    deviceId: Yup.string()
      .required(() => ({ name: "Device Id must be provided." })) 
      .matches(ALPHANUMERIC_REGEX, () => ({name: "Must be a valid device id."})),
  });

  const addDeviceMutation = useMutation((newDevice: CreateDeviceRequest) => devicesService.add(newDevice), {
    onSuccess: () => {
      queryClient.invalidateQueries(devicesService.ID);
      notificationService.success('You have successfully created new device.');

      setResult({ status: 'OK', action: ADD_DEVICE });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const addDevice = (newDevice: CreateDeviceRequest) => addDeviceMutation.mutate(newDevice)

  const classes = useStyles();
  
  return (
    <>
      <Form
        schema={schema}
        onSubmit={addDevice}
      >
        <FormTextInput label="Name" name="name" />
        <FormTextInput label="Device Id" name="deviceId"/>

        <div className={classes.submitButton} >
          <Button type="submit">Submit</Button>
        </div>
        
      </Form>
    </>
  );
}