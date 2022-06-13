import { AxiosError } from "axios";
import { FC, useContext, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { reportTypesLabels } from "../../constants";
import { Button, ConfirmationModal, createEntitiesMap, extractErrorMessage, Modal, NotificationService, Table, TableBody, TableHead, TableRow, useLocationsService } from "../../imports";
import { PredefinedReport } from "../../models";
import { usePredefinedReportsServiceForModifying } from "../../services";
import { AddUpdatePredefinedReportsForm } from "./AddUpdatePredefinedReportsForm";
import { PredefinedReportsContext } from "./PredefinedReports";
import { ADD_PREDEFINED_REPORT, DELETE_PREDEFINED_REPORT, UPDATE_PREDEFINED_REPORT } from "./PredefinedReportsActions";

type Props = {
  groupId: string,
  predefinedReports: PredefinedReport[]
}

const useStyles = createUseStyles({
  container: {
    '& button': {
      margin: '0em 0.5em 0.5em 0.5em'
    }
  },
  buttonAddPredefinedReport: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});

export const PredefinedReportsTable: FC<Props> = ({ groupId, predefinedReports }) => {

  const [isAddOpen, setIsAddOpen] = useState(false);
  const [isDeleteOpen, setDeleteOpen] = useState(false);
  const [selectedPredefinedReport, setSelectedPredefinedReport] = useState<PredefinedReport|undefined>();
  const [locationOptions, setLocationOptions] = useState<any>([]);

  const queryClient = useQueryClient();
  const [predefinedReportsService] = usePredefinedReportsServiceForModifying(groupId, selectedPredefinedReport?.locationId || '');
  const [locationsService] = useLocationsService(groupId);
  const [notificationService] = [new NotificationService()];

  useEffect(() => {
    locationsService.fetchAll().then(locations => setLocationOptions(createEntitiesMap(locations)));
  }, []);

  const { result, setResult } = useContext(PredefinedReportsContext);

  const deleteMutation = useMutation(() => predefinedReportsService.delete(selectedPredefinedReport?.id ?? ''), {
    onSuccess: () => {
      queryClient.invalidateQueries(predefinedReportsService.ID);
      notificationService.success('You have successfully deleted predefined report.');

      setResult({ status: 'OK', action: DELETE_PREDEFINED_REPORT });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));

      setResult({ status: 'ERROR', action: DELETE_PREDEFINED_REPORT });
    }
  });

  useEffect(() => {
    if (!result) return;

    if (result.status === 'OK' && [ADD_PREDEFINED_REPORT, UPDATE_PREDEFINED_REPORT].includes(result.action)) {
      setIsAddOpen(false);
    }

    if (result.action === DELETE_PREDEFINED_REPORT) {
      setDeleteOpen(false);
    }
  }, [result]);

  const onYes = () => deleteMutation.mutate();

  const ActionsButtonGroup = ({ predefinedReport }: any) =>
  <>
    <Button onClick={() => { setSelectedPredefinedReport(predefinedReport); setIsAddOpen(true); }}>Update</Button>
    <Button onClick={() => { setSelectedPredefinedReport(predefinedReport); setDeleteOpen(true); }}>Delete</Button>
  </>

  const classes = useStyles();


  return (
    <div className={classes.container}>
      <Modal title="Add Predefined Report" open={isAddOpen} onClose={() => setIsAddOpen(false)}>
        <AddUpdatePredefinedReportsForm groupId={groupId} existingPredefinedReport={selectedPredefinedReport} isEdit={!!selectedPredefinedReport} />
      </Modal>

      <ConfirmationModal title="Delete predefined report" open={isDeleteOpen} onClose={() => setDeleteOpen(false)} onYes={onYes}>
          <p>Are you sure you want to delete this predefined report?</p>
      </ConfirmationModal>

      <div className={classes.buttonAddPredefinedReport}>
        <Button onClick={() => { setSelectedPredefinedReport(undefined); setIsAddOpen(true); }}>Add Predefined Report</Button>
      </div> 

      <Table hasPagination={false}>
        <TableHead columns={['Name', 'Location', 'Type', 'Resolution', 'Action']}/>
        <TableBody>
          {
            predefinedReports?.map((predefinedReport: PredefinedReport) => 
            <TableRow 
              key={predefinedReport.id}
              cells={[
                predefinedReport.name,
                locationOptions[predefinedReport.locationId]?.name ?? '',
                (reportTypesLabels as any)[predefinedReport.type] || '',
                predefinedReport.type === 'MaxPeriod' ? 'All' : predefinedReport.resolution + '',
                <ActionsButtonGroup predefinedReport={predefinedReport}/>
            ]}/>
            )
          }
        </TableBody>
      </Table>
    </div>
  );
}