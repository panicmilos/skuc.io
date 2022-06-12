import { useEffect, useState } from "react";
import { BACKEND_API, CrudService } from "../imports";
import { CreatePredefinedReport, PredefinedReport, UpdatePredefinedReport } from "../models";


export const PREDEFINED_REPORTS_SERVICE_ID = 'PredefinedReportsService';


export const usePredefinedReportsServiceForFetching = (groupId: string) => {
  
  const [predefinedReportsService, setPredefinedReportsService] = useState(new PredefinedReportsService(groupId));

  useEffect(() => {
    setPredefinedReportsService(new PredefinedReportsService(groupId))
  }, [groupId]);

  return [predefinedReportsService];
}

export const usePredefinedReportsServiceForModifying = (groupId: string, locationId: string) => {
  
  const [predefinedReportsService, setPredefinedReportsService] = useState(new PredefinedReportsService(groupId, locationId));

  useEffect(() => {
    setPredefinedReportsService(new PredefinedReportsService(groupId, locationId))
  }, [groupId, locationId]);

  return [predefinedReportsService];
}

export class PredefinedReportsService extends CrudService<PredefinedReport, CreatePredefinedReport, UpdatePredefinedReport> {

  constructor(groupId: string, locationId: string = '') {
    super(PREDEFINED_REPORTS_SERVICE_ID, `${BACKEND_API}/groups/${groupId}/locations/${locationId ? locationId + '/' : ''}predefined-reports`);
  }

}