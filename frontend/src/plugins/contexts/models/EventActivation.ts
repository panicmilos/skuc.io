export type CreateEventActivationReqest = {
  eventType: string
}

export type UpdateEventActivationRequest = {
  eventType: string
}

export type EventActivation = CreateEventActivationReqest & {
  id: string,
  contextId: string;
}

