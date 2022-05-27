import { useEffect, useState } from "react";
import { BACKEND_API, CrudService } from "../imports";
import { TimePeriodActivation, CreateTimePeriodActivationReqest, UpdateTimePeriodActivationRequest } from "../models";

export const TIME_PERIOD_ACTIVATIONS_SERVICE_ID = 'TimePeriodActivationsService';

export const useTimePeriodActivationsService = (contextId: string, basePath: 'activators' | 'deactivators') => {

  const [timePeriodActivationsService, setTimePeriodActivationsService] = useState(new TimePeriodActivationsService(contextId, basePath));

  useEffect(() => {
    setTimePeriodActivationsService(new TimePeriodActivationsService(contextId, basePath));
  }, [contextId, basePath]);

  return [timePeriodActivationsService];
}

export class TimePeriodActivationsService extends CrudService<TimePeriodActivation, CreateTimePeriodActivationReqest, UpdateTimePeriodActivationRequest> {
  constructor(contextId: string, basePath: 'activators' | 'deactivators' = 'activators') {
    super(TIME_PERIOD_ACTIVATIONS_SERVICE_ID, `${BACKEND_API}/${basePath}/${contextId}/time-period`);
  }
}