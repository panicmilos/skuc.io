import axios from "axios";
import { useEffect, useState } from "react";
import { BACKEND_API } from "../imports";
import { PredefinedReportResult, ReportResult } from "../models";

export const REPORTS_SERVICE_ID = 'ReportsService';

export const useReportsService = (groupId: string, locationId: string) => {

  const [reportsService, setReportsService] = useState(new ReportsService(groupId, locationId));

  useEffect(() => {
    setReportsService(new ReportsService(groupId, locationId));
  }, [groupId, locationId]);

  return [reportsService];
}

export class ReportsService {
  id: string = REPORTS_SERVICE_ID;
  baseUrl: string;

  constructor(groupId: string, locationId: string = '') {
    this.baseUrl = `${BACKEND_API}/groups/${groupId}/locations/${locationId ? locationId + '/' : ''}reports`;
  }

  public async normal(params: any): Promise<ReportResult> {
    return (await axios.post(`${this.baseUrl}/normal`, params)).data;
  }

  public async atSomeTime(params: any): Promise<ReportResult> {
    return (await axios.post(`${this.baseUrl}/at-some-time`, params)).data;
  }

  public async maxPeriod(params: any): Promise<ReportResult> {
    return (await axios.post(`${this.baseUrl}/max-period`, params)).data;
  }

  public async predefined(): Promise<PredefinedReportResult[]> {
    return (await axios.post(`${this.baseUrl}/predefined`)).data;
  }
}