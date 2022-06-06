import { AxiosError } from "axios";
import { FC, useContext, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Button, ConfirmationModal, createEntitiesMap, extractErrorMessage, Modal, NotificationService, Table, TableBody, TableHead, TableRow, useLocationsService } from "../../imports";
import { TemplateInstance } from "../../models";
import { useTemplateInstanceService } from "../../services";
import { AddTemplateInstanceForm } from "./AddTemplateInstanceForm";
import { TemplateInstancesContext } from "./TemplateInstances";
import { ADD_TEMPLATE_INSTANCE, DELETE_TEMPLATE_INSTANCE } from "./TemplateInstancesActions";

type Props = {
  groupId: string,
  templateId: string,
  templateInstances: TemplateInstance[]
}

const useStyles = createUseStyles({
  container: {
    '& button': {
      margin: '0em 0.5em 0.5em 0.5em'
    }
  },
  buttonAddTemplateInstance: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});

export const TemplateInstancesTable: FC<Props> = ({ groupId, templateId, templateInstances }) => {
  
  const [isAddOpen, setIsAddOpen] = useState(false);
  const [isDeleteOpen, setIsDeleteOpen] = useState(false);
  const [selectedTemplateInstance, setSelectedTemplateInstance] = useState<TemplateInstance|undefined>(undefined);

  const queryClient = useQueryClient();

  const [templateInstancesService] = useTemplateInstanceService(groupId, templateId);
  const [locationsService] = useLocationsService(groupId);
  const [notificationService] = useState(new NotificationService());

  const [locationsMap, setLocationsMap] = useState<any>({});

  const { data: locations } = useQuery([locationsService], () => locationsService.fetchAll());
  useEffect(() => {
    setLocationsMap(createEntitiesMap(locations));
  }, [locations]);

  const { result, setResult } = useContext(TemplateInstancesContext);

  const deleteMutation = useMutation(() => templateInstancesService.delete(selectedTemplateInstance?.id ?? ''), {
    onSuccess: () => {
      queryClient.invalidateQueries(templateInstancesService.ID);
      notificationService.success('You have successfully deleted template instance.');

      setResult({ status: 'OK', action:  DELETE_TEMPLATE_INSTANCE });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));

      setResult({ status: 'ERROR', action: DELETE_TEMPLATE_INSTANCE });
    }
  });

  useEffect(() => {
    if (!result) return;

    if (result.status === 'OK' && result.action === ADD_TEMPLATE_INSTANCE) {
      setIsAddOpen(false);
    }

    if (result.action === DELETE_TEMPLATE_INSTANCE) {
      setIsDeleteOpen(false);
    }
  }, [result]);

  const onYes = () => deleteMutation.mutate();

  const ActionsButtonGroup = ({ templateInstance }: any) =>
  <>
    <Button onClick={() => { setSelectedTemplateInstance(templateInstance); setIsDeleteOpen(true); }}>Delete</Button>
  </>

  const classes = useStyles();

  return (
    <div className={classes.container}>

      <Modal title="Instantiate template" open={isAddOpen} onClose={() => setIsAddOpen(false)}>
        <AddTemplateInstanceForm groupId={groupId} templateId={templateId} />
      </Modal>

      <ConfirmationModal title="Delete template instance" open={isDeleteOpen} onClose={() => setIsDeleteOpen(false)} onYes={onYes}>
        <p>Are you sure you want to delete this template instance?</p>
      </ConfirmationModal>

      <div className={classes.buttonAddTemplateInstance}>
        <Button onClick={() => { setSelectedTemplateInstance(undefined); setIsAddOpen(true); }}>Add Template Instance</Button>
      </div>

      <Table hasPagination={false}>
        <TableHead columns={['Location', 'Values', 'Action']}/>
        <TableBody>
          {
            templateInstances?.map((templateInstance: TemplateInstance) => 
            <TableRow 
              key={templateInstance.id}
              cells={[
                locationsMap[templateInstance.locationId]?.name ?? '',
                templateInstance.values.join(','),
                <ActionsButtonGroup templateInstance={templateInstance}/>
              ]}
            />
            )
          }
        </TableBody>
      </Table>

    </div>
  );
}