import { AxiosError } from "axios";
import { FC, useContext, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { useNavigate } from "react-router-dom";
import { Button, ConfirmationModal, DropdownItem, DropdownMenu, extractErrorMessage, Modal, NotificationService, Table, TableBody, TableHead, TableRow } from "../../imports";
import { Configuration, Context } from "../../models"
import { useContextsService } from "../../services";
import { AddUpdateContextForm } from "./AddUpdateContextForm";
import { ADD_CONTEXT, DELETE_CONTEXT, UPDATE_CONTEXT } from "./ContextActions";
import { ContextsContext } from "./Contexts";

type Props = {
  groupId: string,
  contexts: Context[]
};

const useStyles = createUseStyles({
  container: {
    '& button': {
      margin: '0em 0.5em 0.5em 0.5em'
    }
  },
  buttonAddContext: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});

export const ContextsTable: FC<Props> = ({ groupId, contexts }) => {

  const [isAddUpdateOpen, setIsAddUpdateOpen] = useState(false);
  const [isDeleteOpen, setDeleteOpen] = useState(false);
  const [selectedContext, setSelectedContext] = useState<Context|undefined>(undefined);

  const queryClient = useQueryClient();

  const [contextsService] = useContextsService(groupId);
  const [notificationService] = useState(new NotificationService());

  const { result, setResult } = useContext(ContextsContext);

  const deleteMutation = useMutation(() => contextsService.delete(selectedContext?.id ?? ''), {
    onSuccess: () => {
      queryClient.invalidateQueries(contextsService.ID);
      notificationService.success('You have successfully deleted context.');

      setResult({ status: 'OK', action:  DELETE_CONTEXT });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));

      setResult({ status: 'ERROR', action: DELETE_CONTEXT });
    }
  });

  useEffect(() => {
    if (!result) return;

    if (result.status === 'OK' && [ADD_CONTEXT, UPDATE_CONTEXT].includes(result.action)) {
      setIsAddUpdateOpen(false);
    }

    if (result.action === DELETE_CONTEXT) {
      setDeleteOpen(false);
    }
  }, [result]);

  const onYes = () => deleteMutation.mutate();

  const nav = useNavigate();

  const ActionsButtonGroup = ({ context }: any) =>
  <>
    <Button onClick={() => { setSelectedContext(context); setIsAddUpdateOpen(true); }}>Update</Button>
    <Button onClick={() => { setSelectedContext(context); setDeleteOpen(true); }}>Delete</Button>
    <DropdownMenu title={"Context Activations"}>
      <DropdownItem title="Event Activators" onClick={() => { nav(`/groups/${groupId}/contexts/${context.id}/event-activators`) }}></DropdownItem>
      <DropdownItem title="Time Period Activators" onClick={() => { nav(`/groups/${groupId}/contexts/${context.id}/time-period-activators`) }}></DropdownItem>
      <DropdownItem title="Event Deactivators" onClick={() => { nav(`/groups/${groupId}/contexts/${context.id}/event-deactivators`) }}></DropdownItem>
      <DropdownItem title="Time Period Deactivators" onClick={() => { nav(`/groups/${groupId}/contexts/${context.id}/time-period-deactivators`) }}></DropdownItem>
    </DropdownMenu>
  </>
  
  const classes = useStyles();

  const generateDescription = (configuration: Configuration) => `${Object.keys(configuration?.thresholdConfiguration ?? {}).length} Thresholds and ${Object.keys(configuration?.statusConfiguration ?? {}).length} Status configurations`;

  return (
    <div className={classes.container}>
      <Modal title={!selectedContext ? "Add context" : "Update context"} open={isAddUpdateOpen} onClose={() => setIsAddUpdateOpen(false)}>
        <AddUpdateContextForm groupId={groupId} existingContext={selectedContext} isEdit={!!selectedContext} />
      </Modal>

      <ConfirmationModal title="Delete context" open={isDeleteOpen} onClose={() => setDeleteOpen(false)} onYes={onYes}>
        <p>Are you sure you want to delete this context?</p>
      </ConfirmationModal>

      <div className={classes.buttonAddContext}>
        <Button onClick={() => { setSelectedContext(undefined); setIsAddUpdateOpen(true); }}>Add Context</Button>
      </div> 

      <Table hasPagination={false}>
        <TableHead columns={['Name', 'Details', 'Action']}/>
        <TableBody>
          {
            contexts?.map((context: Context) => 
            <TableRow 
            key={context.id}
            cells={[
              context.name,
              generateDescription(context.configuration),
              <ActionsButtonGroup context={context}/>
            ]}/>
            )
          }
        </TableBody>
      </Table>
      
    </div>
  );
}