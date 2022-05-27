import { AxiosError } from "axios";
import { FC, useContext, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { Button, ConfirmationModal, extractErrorMessage, Modal, NotificationService, Table, TableBody, TableHead, TableRow } from "../../imports";
import { TimePeriodActivation } from "../../models"
import { useTimePeriodActivationsService } from "../../services";
import { AddUpdateTimePeriodActivationForm } from "./AddUpdateTimePeriodActivationForm";
import { ADD_TIME_PERIOD_ACTIVATION, DELETE_TIME_PERIOD_ACTIVATION, UPDATE_TIME_PERIOD_ACTIVATION } from "./TimePeriodActivationActions";
import { TimePeriodActivationsContext } from "./TimePeriodActivations";

type Props = {
  contextId: string,
  timePeriodActivations: TimePeriodActivation[]
  type: 'activators' | 'deactivators';
}

const useStyles = createUseStyles({
  container: {
    '& button': {
      margin: '0em 0.5em 0.5em 0.5em'
    }
  },
  buttonAddTimePeriodActivation: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});

export const TimePeriodActivationsTable: FC<Props> = ({ contextId, timePeriodActivations, type }) => {

  const [isAddUpdateOpen, setIsAddUpdateOpen] = useState(false);
  const [isDeleteOpen, setDeleteOpen] = useState(false);
  const [selectedTimePeriodActivation, setSelectedTimePeriodActivation] = useState<TimePeriodActivation|undefined>(undefined);

  const queryClient = useQueryClient();

  const [timePeriodActivationsService] = useTimePeriodActivationsService(contextId, type);
  const [notificationService] = useState(new NotificationService());

  const { result, setResult } = useContext(TimePeriodActivationsContext);

  const deleteMutation = useMutation(() => timePeriodActivationsService.delete(selectedTimePeriodActivation?.id ?? ''), {
    onSuccess: () => {
      queryClient.invalidateQueries(timePeriodActivationsService.ID);
      notificationService.success('You have successfully deleted timePeriod activation.');

      setResult({ status: 'OK', action:  DELETE_TIME_PERIOD_ACTIVATION });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));

      setResult({ status: 'ERROR', action: DELETE_TIME_PERIOD_ACTIVATION });
    }
  });

  useEffect(() => {
    if (!result) return;

    if (result.status === 'OK' && [ADD_TIME_PERIOD_ACTIVATION, UPDATE_TIME_PERIOD_ACTIVATION].includes(result.action)) {
      setIsAddUpdateOpen(false);
    }

    if (result.action === DELETE_TIME_PERIOD_ACTIVATION) {
      setDeleteOpen(false);
    }
  }, [result]);

  const onYes = () => deleteMutation.mutate();

  const ActionsButtonGroup = ({ timePeriodActivation }: any) =>
  <>
    <Button onClick={() => { setSelectedTimePeriodActivation(timePeriodActivation); setIsAddUpdateOpen(true); }}>Update</Button>
    <Button onClick={() => { setSelectedTimePeriodActivation(timePeriodActivation); setDeleteOpen(true); }}>Delete</Button>
  </>
  
  const classes = useStyles();
  const activationName = type === 'activators' ? 'activator' : 'deactivator';

  return (
    <div className={classes.container}>
      <Modal title={!selectedTimePeriodActivation ? `Add timePeriod ${activationName}` : `Update timePeriod ${activationName}`} open={isAddUpdateOpen} onClose={() => setIsAddUpdateOpen(false)}>
        <AddUpdateTimePeriodActivationForm type={type} contextId={contextId} existingTimePeriodActivation={selectedTimePeriodActivation} isEdit={!!selectedTimePeriodActivation} />
      </Modal>

      <ConfirmationModal title={`Delete timePeriod ${activationName}`} open={isDeleteOpen} onClose={() => setDeleteOpen(false)} onYes={onYes}>
        <p>Are you sure you want to delete this timePeriod {activationName}?</p>
      </ConfirmationModal>

      <div className={classes.buttonAddTimePeriodActivation}>
        <Button onClick={() => { setSelectedTimePeriodActivation(undefined); setIsAddUpdateOpen(true); }}>Add TimePeriod {activationName}</Button>
      </div> 

      <Table hasPagination={false}>
        <TableHead columns={['Cron Start', 'Cron End', 'Action']}/>
        <TableBody>
          {
            timePeriodActivations?.map((timePeriodActivation: TimePeriodActivation) => 
            <TableRow 
            key={timePeriodActivation.id}
            cells={[
              timePeriodActivation.cronStart,
              timePeriodActivation.cronEnd,
              <ActionsButtonGroup timePeriodActivation={timePeriodActivation}/>
            ]}/>
            )
          }
        </TableBody>
      </Table>
      
    </div>
  );
}