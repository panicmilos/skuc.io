export type ReportResult = {
  groups: ReportResultGroup[]
}

export type ReportResultGroup = {
  paramName: string,
  algorithm: string,
  reportResultValues: ReportResultValue[]
}

export type ReportResultValue = {
  value: number,
  resolution: number,
  createdAt: string
}

export type PredefinedReportResult = {
  report: ReportResult,
  reportType: string,
  name: string
}