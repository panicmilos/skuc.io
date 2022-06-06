import { AxiosError } from "axios";
import { FC, useContext, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { Button, ConfirmationModal, extractErrorMessage, Modal, NotificationService, Table, TableBody, TableHead, TableRow } from "../../imports";
import { Template } from "../../models"
import { useTemplatesService } from "../../services";
import { AddTemplateForm } from "./AddTemplateForm";
import { PreviewTemplateForm } from "./PreviewTemplateForm";
import { ADD_TEMPLATE, DELETE_TEMPLATE } from "./TemplateActions";
import { TemplatesContext } from "./Templates";

type Props = {
  groupId: string,
  templates: Template[]
}

const useStyles = createUseStyles({
  container: {
    '& button': {
      margin: '0em 0.5em 0.5em 0.5em'
    }
  },
  buttonAddTemplate: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});

export const TemplatesTable: FC<Props> = ({ groupId, templates }) => {

  const [isAddOpen, setIsAddOpen] = useState(false);
  const [isPreviewOpen, setIsPreviewOpen] = useState(false);
  const [isDeleteOpen, setIsDeleteOpen] = useState(false);
  const [selectedTemplate, setSelectedTemplate] = useState<Template|undefined>(undefined);

  const queryClient = useQueryClient();

  const [templatesService] = useTemplatesService(groupId);
  const [notificationService] = useState(new NotificationService());

  const { result, setResult } = useContext(TemplatesContext);

  const deleteMutation = useMutation(() => templatesService.delete(selectedTemplate?.id ?? ''), {
    onSuccess: () => {
      queryClient.invalidateQueries(templatesService.ID);
      notificationService.success('You have successfully deleted template.');

      setResult({ status: 'OK', action:  DELETE_TEMPLATE });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));

      setResult({ status: 'ERROR', action: DELETE_TEMPLATE });
    }
  });

  useEffect(() => {
    if (!result) return;

    if (result.status === 'OK' && result.action === ADD_TEMPLATE) {
      setIsAddOpen(false);
    }

    if (result.action === DELETE_TEMPLATE) {
      setIsDeleteOpen(false);
    }
  }, [result]);

  const onYes = () => deleteMutation.mutate();

  const ActionsButtonGroup = ({ template }: any) =>
  <>
    <Button onClick={() => { setSelectedTemplate(template); setIsPreviewOpen(true); }}>Preview</Button>
    <Button onClick={() => { setSelectedTemplate(template); setIsDeleteOpen(true); }}>Delete</Button>
  </>
  
  const classes = useStyles();

  return (
    <div className={classes.container}>

      <Modal title="Add template" open={isAddOpen} onClose={() => setIsAddOpen(false)}>
        <AddTemplateForm groupId={groupId} />
      </Modal>

      <Modal title="Preview template" open={isPreviewOpen} onClose={() => setIsPreviewOpen(false)}>
        <PreviewTemplateForm template={selectedTemplate} />
      </Modal>


      <ConfirmationModal title="Delete templates" open={isDeleteOpen} onClose={() => setIsDeleteOpen(false)} onYes={onYes}>
        <p>Are you sure you want to delete this templates?</p>
      </ConfirmationModal>

      <div className={classes.buttonAddTemplate}>
        <Button onClick={() => { setSelectedTemplate(undefined); setIsAddOpen(true); }}>Add Template</Button>
      </div>

      <Table hasPagination={false}>
        <TableHead columns={['Name', 'Parameters', 'Action']}/>
        <TableBody>
          {
            templates?.map((template: Template) => 
            <TableRow 
              key={template.id}
              cells={[
                template.name,
                template.parameters.join(','),
                <ActionsButtonGroup template={template}/>
              ]}
            />
            )
          }
        </TableBody>
      </Table>

    </div>
  );
}