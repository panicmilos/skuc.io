import { FC, useContext, useEffect, useState } from "react";
import { useLocationsService } from "../../../locations";
import { EventsSocketIoClient } from "../../clients";
import { AuthContext, Card, Col, Container, createEntitiesMap, getGroupIdFromToken, Location } from "../../imports";
import { LiveReportWrapper } from "./LiveReportWrapper";
import { SelectReportForm } from "./SelectReportForm";


export const Live: FC = () => {

  const groupId = getGroupIdFromToken();

  const [selectedReports, setSelectedReports] = useState<any>([]);
  const [clients, setClients] = useState<EventsSocketIoClient[]>([]);

  const addReport = (report: any) => {
    setSelectedReports([
      ...selectedReports,
      report
    ]);
  };

  const addClient = (client: EventsSocketIoClient) => {
    setClients([
      ...clients,
      client
    ]);
  }

  const removeReportListener = (report: any) => {
    const index = selectedReports.findIndex((r: any) => r.paramName === report.paramName && r.locationId === report.locationId);
    if (index === -1) return;
      clients[index].close();

    selectedReports.splice(index, 1);
    clients.splice(index, 1);

    setSelectedReports([...selectedReports]);
    setClients([...clients]);
  }

  const [newReport, setNewReport] = useState<any>(null);

  useEffect(() => {
    if (newReport) {
      const newClient = new EventsSocketIoClient(newReport.locationId);
      addClient(newClient);
      addReport(newReport);
      setNewReport(null);
    }
  }, [newReport]);

  const [locationsService] = useLocationsService(groupId);
  const [locationLabels, setLocationLabels] = useState<any>([]);  
  useEffect(() => {
    locationsService.fetchAll().then(locations => setLocationLabels(createEntitiesMap(locations)));
  }, [groupId, locationsService]);


  return (
    <>
      <Container alignItems="center">
        <Col all={3} />
        <Col all={6} >
          <Card>
            <SelectReportForm groupId={groupId} submit={setNewReport} />
          </Card>
        </Col>
        <Col all={3} />
      </Container>

      {
        selectedReports.map((report: any, i: number) => <LiveReportWrapper
          key={i}
          paramName={report.paramName || ''}
          locationName={locationLabels[report.locationId]?.name || ''}
          client={clients[i]}
          close={() => removeReportListener(report)}
        />)
      }

    </>
  );
};