import { useEffect, useState } from "react";
import { BACKEND_API, CrudService } from "../imports";
import { EventActivation, CreateEventActivationReqest, UpdateEventActivationRequest } from "../models";

export const EVENT_ACTIVATIONS_SERVICE_ID = 'EventActivationsService';

export const useEventActivationsService = (contextId: string, basePath: 'activators' | 'deactivators') => {

  const [contextsService, setEventActivationsService] = useState(new EventActivationsService(contextId, basePath));

  useEffect(() => {
    setEventActivationsService(new EventActivationsService(contextId, basePath));
  }, [contextId, basePath]);

  return [contextsService];
}

export class EventActivationsService extends CrudService<EventActivation, CreateEventActivationReqest, UpdateEventActivationRequest> {
  constructor(contextId: string, basePath: 'activators' | 'deactivators' = 'activators') {
    super(EVENT_ACTIVATIONS_SERVICE_ID, `${BACKEND_API}/${basePath}/${contextId}/event`);
  }
}