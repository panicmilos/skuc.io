import { FC } from "react";
import { useQuery } from "react-query";
import { MultiLineReport } from "../../components";
import { Card, getGroupIdFromToken } from "../../imports";
import { useReportsService } from "../../services";
import { mapReportToChartData } from "../../utils";

export const QuickReports: FC = () => {

  const groupId = getGroupIdFromToken();

  const [reportsSevice] = useReportsService(groupId, '');

  const { data: reports } = useQuery([reportsSevice], () => reportsSevice.predefined());

  return (
    <>
      {
        reports && reports?.map(report => 
          <Card title={report.name}>
            <MultiLineReport
              {...mapReportToChartData(report.report, report.reportType)}
              dataUnits={{}}
            />
          </Card>
        )
      }
    </>
  );
}