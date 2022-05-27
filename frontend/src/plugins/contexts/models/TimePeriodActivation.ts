export type CreateTimePeriodActivationReqest = {
  cronStart: string;
  cronEnd: string;
}

export type UpdateTimePeriodActivationRequest = {
  cronStart: string;
  cronEnd: string;
}

export type TimePeriodActivation = CreateTimePeriodActivationReqest & {
  id: string,
  contextId: string;
}

