import moment from "moment";
import { ReportResult, ReportResultGroup, ReportResultValue } from "../models";

export function mapParamFiltersForRequest(paramFilters: any) {
  return paramFilters?.map((paramFilter: any) => {
    return {
      ...paramFilter,
      valueFilters: (paramFilter.filters && paramFilter.filters.split(", ").map((filter: any) => {
        const tokens = filter.split(/\s+/)
        return {
          algorithm: tokens[0],
          comparator: tokens[1],
          value: parseFloat(tokens[2])
        }
      })) || []
    };
  })
}


export function mapParamFiltersForComponents(paramFilters: any) {
  console.log(paramFilters);

  return paramFilters.map((paramFilter: any) => ({
    paramName: paramFilter.paramName,
    algorithm: paramFilter.algorithm,
    filters: paramFilter.valueFilters.map(({algorithm, comparator, value}: any) => `${algorithm} ${comparator} ${value}`).join(", ")
  }));
}

export function mapPeriodForRequest({ from, to }: any) {
  return {
    ...(from ? {from: moment(from).format() } : {}),
    ...(to ? {to: moment(to).endOf('day').format() } : {}),
  }
}

export function mapReportToChartData(report: ReportResult, reportType: string) {
  const nameKey = "createdAt";
  const keys = report?.groups.map((group: any) => `${group.algorithm} ${group.paramName}`);
  
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