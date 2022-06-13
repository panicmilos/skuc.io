import { FC, useState } from "react";
import { useQuery, useQueryClient } from "react-query";
import { MultiLineReport } from "../../components";
import { Card, Col, Container, getGroupIdFromToken } from "../../imports";
import { ReportResult, } from "../../models";
import { useReportsService } from "../../services";
import { mapReportToChartData } from "../../utils";
import { ReportFiltersForm } from "./ReportFiltersForm";

export const Reports: FC = () => {

  const groupId = getGroupIdFromToken();

  const [report, setReport] = useState<ReportResult|undefined>();
  const [reportParams, setReportParams] = useState<any>();
  const [shouldFetch, setShouldFetch] = useState(false);

  const queryClient = useQueryClient();
  const [reportsService] = useReportsService(groupId, reportParams?.locationId ?? '');

  useQuery([shouldFetch, reportsService], () => {
    if (reportParams.type === 'Normal') return reportsService.normal(reportParams);
    if (reportParams.type === 'AtSomePointInTheTime') return reportsService.atSomeTime(reportParams);
    if (reportParams.type === 'MaxPeriod') return reportsService.maxPeriod(reportParams);
  }, { enabled: shouldFetch, onSuccess: (report: ReportResult) => { setReport(report); setShouldFetch(false); }, onError: () => setShouldFetch(false) });

  const onSubmit = (params: any) => {
    queryClient.invalidateQueries(reportsService.id);
    setReportParams(params);
    setShouldFetch(true);
  }

  return (
    <>
      <Container alignItems="center">
        <Col all={3} />
        <Col all={6} >
          <Card>
            <ReportFiltersForm groupId={groupId} submit={onSubmit} />
          </Card>
        </Col>
        <Col all={3} />
      </Container>

      {
        report &&
          <MultiLineReport
            {...mapReportToChartData(report, reportParams?.type || '')}
            dataUnits={{}}
          />
      }

      
    </>
  )
}