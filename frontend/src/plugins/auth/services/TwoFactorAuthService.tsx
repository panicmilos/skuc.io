import axios from "axios";
import { Service } from "../../../core/services/service";
import { USERS_SERVICE_URL } from "../../../urls";
import { TwoFactResponse } from "../models/TwoFactResponse";

export const TWO_FACTOR_AUTH_SERVICE_ID = 'TwoFactorAuthService';

export class TwoFactorAuthService extends Service {
  constructor(groupId: string) {
    super(TWO_FACTOR_AUTH_SERVICE_ID, `${USERS_SERVICE_URL}/groups/${groupId}`)
  }

  public async getQrFor(userId: string): Promise<string> {
    return (await axios.get(`${this.baseUrl}/users/${userId}/2fact/qr`)).data;
  }

  public async activate(userId: string): Promise<TwoFactResponse> {
    return (await axios.post(`${this.baseUrl}/users/${userId}/2fact/activate`)).data;
  }

  public async hide(userId: string): Promise<TwoFactResponse> {
    return (await axios.post(`${this.baseUrl}/users/${userId}/2fact/qr/hide`)).data;
  }

  public async reset(userId: string, code: string): Promise<TwoFactResponse> {
    return (await axios.put(`${this.baseUrl}/users/${userId}/2fact/reset`, {}, { headers: { "2FactCode": code } })).data;
  }

  public async deactivate(userId: string, code: string): Promise<TwoFactResponse> {
    return (await axios.delete(`${this.baseUrl}/users/${userId}/2fact/deactivate`, { headers: { "2FactCode": code } })).data;
  }
}