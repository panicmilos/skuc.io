import { AxiosError } from "axios";
import { FC, useContext, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { Link } from "react-router-dom";
import { Button, ConfirmationModal, extractErrorMessage, Modal, NotificationService, Table, TableBody, TableHead, TableRow } from "../../imports";
import { Location } from "../../models"
import { useLocationsService } from "../../services";
import { AddUpdateLocationForm } from "./AddUpdateLocationForm";
import { ADD_LOCATION, DELETE_LOCATION, UPDATE_LOCATION } from "./LocationActions";
import { LocationsContext } from "./Locations";


type Props = {
  groupId: string,
  locations: Location[]
}

const useStyles = createUseStyles({
  container: {
    '& button': {
      margin: '0em 0.5em 0.5em 0.5em'
    }
  },
  buttonAddLocation: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});

export const LocationsTable: FC<Props> = ({ groupId, locations }) => {

  const [isAddUpdateOpen, setIsAddUpdateOpen] = useState(false);
  const [isDeleteOpen, setDeleteOpen] = useState(false);
  const [selectedLocation, setSelectedLocation] = useState<Location|undefined>(undefined);

  const queryClient = useQueryClient();
  const [locationsService] = useLocationsService(groupId);
  const [notificationService] = useState(new NotificationService());

  const { result, setResult } = useContext(LocationsContext);

  const deleteMutation = useMutation(() => locationsService.delete(selectedLocation?.id ?? ''), {
    onSuccess: () => {
      queryClient.invalidateQueries(locationsService.ID);
      notificationService.success('You have successfully deleted location.');

      setResult({ status: 'OK', action: DELETE_LOCATION});
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));

      setResult({ status: 'ERROR', action: DELETE_LOCATION});
    }
  });

  useEffect(() => {
    if (!result) return;

    if (result.status === 'OK' && [ADD_LOCATION, UPDATE_LOCATION].includes(result.action)) {
      setIsAddUpdateOpen(false);
    }

    if (result.action === DELETE_LOCATION) {
      setDeleteOpen(false);
    }
  }, [result]); 

  const onYes = () => deleteMutation.mutate();

  const ActionsButtonGroup = ({location}: any) =>
  <>
    <Button onClick={() => {setSelectedLocation(location); setIsAddUpdateOpen(true); }}>Update</Button>
    <Button onClick={() => {setSelectedLocation(location); setDeleteOpen(true); }}>Delete</Button>
    <Link to={`/groups/${groupId}/locations/${location.id}/devices`}><Button>Devices</Button></Link>
    <Link to={`/groups/${groupId}/locations/${location.id}/diagrams`}><Button>Diagram</Button></Link>
  </>
  
  const classes = useStyles();

  return (
    <div className={classes.container}>
      <Modal title={!selectedLocation ? "Add location" : "Update location"} open={isAddUpdateOpen} onClose={() => setIsAddUpdateOpen(false)}>
        <AddUpdateLocationForm groupId={groupId} existingLocation={selectedLocation} isEdit={!!selectedLocation} />
      </Modal>

      <ConfirmationModal title="Delete location" open={isDeleteOpen} onClose={() => setDeleteOpen(false)} onYes={onYes}>
          <p>Are you sure you want to delete this location?</p>
      </ConfirmationModal>

      <div className={classes.buttonAddLocation}>
        <Button onClick={() => { setSelectedLocation(undefined); setIsAddUpdateOpen(true); }}>Add Location</Button>
      </div> 

      <Table hasPagination={false}>
        <TableHead columns={['Name', 'Longitude', 'Latitude', 'Action']}/>
        <TableBody>
          {
            locations?.map((location: Location) => 
            <TableRow 
              key={location.id}
              cells={[
                location.name,
                location.lng + '',
                location.lat + '',
                <ActionsButtonGroup location={location}/>
            ]}/>
            )
          }
        </TableBody>
      </Table>
    </div>
  );
}