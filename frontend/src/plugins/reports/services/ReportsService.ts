import axios from "axios";
import { useEffect, useState } from "react";
import { BACKEND_API } from "../imports";
import { ReportResult } from "../models";

export const REPORTS_SERVICE_ID = 'ReportsService';

export const useReportsService = () => {

  const [reportsService, setReportsService] = useState(new ReportsService());

  useEffect(() => {
    setReportsService(new ReportsService());
  }, []);

  return [reportsService];
}

export class ReportsService {
  id: string = REPORTS_SERVICE_ID;
  baseUrl: string;

  constructor() {
    this.baseUrl = `${BACKEND_API}/reports`;
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
}