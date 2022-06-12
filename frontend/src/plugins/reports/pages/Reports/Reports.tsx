import moment from "moment";
import { FC, useState } from "react";
import { useQuery, useQueryClient } from "react-query";
import { MultiLineReport } from "../../components";
import { Card, Col, Container, getGroupIdFromToken } from "../../imports";
import { ReportResult, ReportResultGroup, ReportResultValue } from "../../models";
import { useReportsService } from "../../services";
import { ReportFiltersForm } from "./ReportFiltersForm";

export const Reports: FC = () => {

  const groupId = getGroupIdFromToken();

  const queryClient = useQueryClient();
  const [reportsService] = useReportsService();

  const [report, setReport] = useState<ReportResult|undefined>();
  const [reportParams, setReportParams] = useState<any>();
  const [shouldFetch, setShouldFetch] = useState(false);

  useQuery([shouldFetch], () => {
    if (reportParams.type === 'Normal') return reportsService.normal(reportParams);
    if (reportParams.type === 'AtSomePointInTheTime') return reportsService.atSomeTime(reportParams);
    if (reportParams.type === 'MaxPeriod') return reportsService.maxPeriod(reportParams);
  }, { enabled: shouldFetch, onSuccess: (report: ReportResult) => { setReport(report); setShouldFetch(false); }, onError: () => setShouldFetch(false) });

  const onSubmit = (params: any) => {
    queryClient.invalidateQueries(reportsService.id);
    setReportParams(params);
    setShouldFetch(true);
  }

  const mapRecordsToChartData = (report: any, reportType: string) => {
    const nameKey = "createdAt";
    const keys = reportParams?.paramFilters.map((paramFilter: any) => `${paramFilter.algorithm} ${paramFilter.paramName}`);
    
    const times: string[] = report?.groups?.flatMap((group: ReportResultGroup) => {

      const normalCreatedAts = group.reportResultValues.map(value => moment(value.createdAt).format())
      const previousCreatedAts = reportType === 'MaxPeriod' ? group.reportResultValues.map(value => moment(value.createdAt).subtract(value.resolution, 'minutes').format()) : [];

      return [...previousCreatedAts, ...normalCreatedAts]

    }) || [];

    const sorted_times = [...new Set(times)].sort();

    const data = (sorted_times) ? sorted_times.map((time: string) => {
      const record: any = {};

      record[nameKey] = moment(time).format('DD.MM.YYYY. HH:mm');
      keys.forEach((k: string) => {
        var rightGroup = report?.groups?.find((group: ReportResultGroup) => {
          const [algorithm, paramName] = k.split(' ');
          return group.paramName === paramName && group.algorithm === algorithm;
        });

        if (rightGroup == null) {
          record[k] = 0;
          return;
        }

        var reportValue = rightGroup.reportResultValues.find((reportValue: ReportResultValue) => moment(reportValue.createdAt).format() === time || (reportType === 'MaxPeriod' && moment(reportValue.createdAt).subtract(reportValue.resolution, 'minutes').format() === time)); 
        if (reportValue === null)
          record[k] = 0;
        else
          record[k] = reportValue?.value ?? 0;
      });

      return record;
    }) : [];
  
    return {
      nameKey,
      dataKeys: keys,
      data
    }
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
            {...mapRecordsToChartData(report, reportParams?.type || '')}
            dataUnits={{}}
          />
      }

      
    </>
  )
}