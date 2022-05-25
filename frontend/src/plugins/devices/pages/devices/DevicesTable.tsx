import { AxiosError } from "axios";
import { FC, useContext, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { Button, ConfirmationModal, extractErrorMessage, Modal, NotificationService, Table, TableBody, TableHead, TableRow } from "../../imports";
import { Device } from "../../models"
import { useDevicesService } from "../../services";
import { AddDeviceForm } from "./AddDeviceForm";
import { ADD_DEVICE, DELETE_DEVICE } from "./DeviceActions";
import { DevicesContext } from "./Devices";

type Props = {
  groupId: string,
  locationId: string,
  devices: Device[]
}

const useStyles = createUseStyles({
  container: {
    '& button': {
      margin: '0em 0.5em 0.5em 0.5em'
    }
  },
  buttonAddDevice: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});

export const DevicesTable: FC<Props> = ({ groupId, locationId, devices }) => {

  const [isAddOpen, setIsAddOpen] = useState(false);
  const [isDeleteOpen, setDeleteOpen] = useState(false);
  const [selectedDevice, setSelectedDevice] = useState<Device|undefined>(undefined);

  const queryClient = useQueryClient();
  const [devicesService] = useDevicesService(groupId, locationId);
  const [notificationService] = useState(new NotificationService());

  const { result, setResult } = useContext(DevicesContext);

  const deleteMutation = useMutation(() => devicesService.delete(selectedDevice?.id ?? ''), {
    onSuccess: () => {
      queryClient.invalidateQueries(devicesService.ID);
      notificationService.success('You have successfully deleted device.');

      setResult({ status: 'OK', action: DELETE_DEVICE });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));

      setResult({ status: 'ERROR', action: DELETE_DEVICE });
    }
  });

  useEffect(() => {
    if (!result) return;

    if (result.status === 'OK' && result.action === ADD_DEVICE) {
      setIsAddOpen(false);
    }

    if (result.action === DELETE_DEVICE) {
      setDeleteOpen(false);
    }
  }, [result]);

  const onYes = () => deleteMutation.mutate();

  const ActionsButtonGroup = ({device}: any) =>
  <>
    <Button onClick={() => { setSelectedDevice(device); setDeleteOpen(true); }}>Delete</Button>
  </>

  const classes = useStyles();

  return (
    <div className={classes.container}>
      <Modal title="Add device" open={isAddOpen} onClose={() => setIsAddOpen(false)}>
        <AddDeviceForm groupId={groupId} locationId={locationId} />
      </Modal>

      <ConfirmationModal title="Delete device" open={isDeleteOpen} onClose={() => setDeleteOpen(false)} onYes={onYes}>
          <p>Are you sure you want to delete this device?</p>
      </ConfirmationModal>

      <div className={classes.buttonAddDevice}>
        <Button onClick={() => { setSelectedDevice(undefined); setIsAddOpen(true); }}>Add Device</Button>
      </div> 

      <Table hasPagination={false}>
        <TableHead columns={['Name', 'Id', 'Action']}/>
        <TableBody>
          {
            devices?.map((device: Device) => 
            <TableRow 
              key={device.id}
              cells={[
                device.name,
                device.deviceId,
                <ActionsButtonGroup device={device}/>
            ]}/>
            )
          }
        </TableBody>
      </Table>
    </div>
  );
}