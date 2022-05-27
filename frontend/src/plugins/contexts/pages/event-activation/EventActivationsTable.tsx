import { AxiosError } from "axios";
import { FC, useContext, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { Button, ConfirmationModal, extractErrorMessage, Modal, NotificationService, Table, TableBody, TableHead, TableRow } from "../../imports";
import { EventActivation } from "../../models"
import { useEventActivationsService } from "../../services";
import { AddUpdateEventActivationForm } from "./AddUpdateEventActivationForm";
import { ADD_EVENT_ACTIVATION, DELETE_EVENT_ACTIVATION, UPDATE_EVENT_ACTIVATION } from "./EventActivationActions";
import { EventActivationsContext } from "./EventActivations";

type Props = {
  contextId: string,
  eventActivations: EventActivation[]
  type: 'activators' | 'deactivators';
}

const useStyles = createUseStyles({
  container: {
    '& button': {
      margin: '0em 0.5em 0.5em 0.5em'
    }
  },
  buttonAddEventActivation: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});

export const EventActivationsTable: FC<Props> = ({ contextId, eventActivations, type }) => {

  const activationName = type === 'activators' ? 'activator' : 'deactivator';

  const [isAddUpdateOpen, setIsAddUpdateOpen] = useState(false);
  const [isDeleteOpen, setDeleteOpen] = useState(false);
  const [selectedEventActivation, setSelectedEventActivation] = useState<EventActivation|undefined>(undefined);

  const queryClient = useQueryClient();

  const [eventActivationsService] = useEventActivationsService(contextId, type);
  const [notificationService] = useState(new NotificationService());

  const { result, setResult } = useContext(EventActivationsContext);

  const deleteMutation = useMutation(() => eventActivationsService.delete(selectedEventActivation?.id ?? ''), {
    onSuccess: () => {
      queryClient.invalidateQueries(eventActivationsService.ID);
      notificationService.success(`You have successfully deleted event ${activationName}.`);

      setResult({ status: 'OK', action:  DELETE_EVENT_ACTIVATION });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));

      setResult({ status: 'ERROR', action: DELETE_EVENT_ACTIVATION });
    }
  });

  useEffect(() => {
    if (!result) return;

    if (result.status === 'OK' && [ADD_EVENT_ACTIVATION, UPDATE_EVENT_ACTIVATION].includes(result.action)) {
      setIsAddUpdateOpen(false);
    }

    if (result.action === DELETE_EVENT_ACTIVATION) {
      setDeleteOpen(false);
    }
  }, [result]);

  const onYes = () => deleteMutation.mutate();

  const ActionsButtonGroup = ({ eventActivation }: any) =>
  <>
    <Button onClick={() => { setSelectedEventActivation(eventActivation); setIsAddUpdateOpen(true); }}>Update</Button>
    <Button onClick={() => { setSelectedEventActivation(eventActivation); setDeleteOpen(true); }}>Delete</Button>
  </>
  
  const classes = useStyles();

  return (
    <div className={classes.container}>
      <Modal title={!selectedEventActivation ? `Add event ${activationName}` : `Update event ${activationName}`} open={isAddUpdateOpen} onClose={() => setIsAddUpdateOpen(false)}>
        <AddUpdateEventActivationForm type={type} contextId={contextId} existingEventActivation={selectedEventActivation} isEdit={!!selectedEventActivation} />
      </Modal>

      <ConfirmationModal title={`Delete event ${activationName}`} open={isDeleteOpen} onClose={() => setDeleteOpen(false)} onYes={onYes}>
        <p>Are you sure you want to delete this event {activationName}?</p>
      </ConfirmationModal>

      <div className={classes.buttonAddEventActivation}>
        <Button onClick={() => { setSelectedEventActivation(undefined); setIsAddUpdateOpen(true); }}>Add Event {activationName}</Button>
      </div> 

      <Table hasPagination={false}>
        <TableHead columns={['Name', 'Action']}/>
        <TableBody>
          {
            eventActivations?.map((eventActivation: EventActivation) => 
            <TableRow 
            key={eventActivation.id}
            cells={[
              eventActivation.eventType,
              <ActionsButtonGroup eventActivation={eventActivation}/>
            ]}/>
            )
          }
        </TableBody>
      </Table>
      
    </div>
  );
}