import { useEffect, useState } from "react";
import { BACKEND_API, CrudService } from "../imports";
import { CreateDeviceRequest, Device, UpdateDeviceRequest } from "../models";

export const DEVICES_SERVICE_ID = 'DevicesService';

export const useDevicesService = (groupId: string, locationId: string) => {
  
  const [devicesService, setDevicesService] = useState(new DevicesService(groupId, locationId));

  useEffect(() => {
    setDevicesService(new DevicesService(groupId, locationId))
  }, [groupId, locationId]);

  return [devicesService];
}

export class DevicesService extends CrudService<Device, CreateDeviceRequest, UpdateDeviceRequest> {
  constructor(groupId: string, locationId: string) {
    super(DEVICES_SERVICE_ID, `${BACKEND_API}/groups/${groupId}/locations/${locationId}/devices`)
  }
}