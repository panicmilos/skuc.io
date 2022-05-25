export type CreateDeviceRequest = {
  name: string,
  deviceId: string
}

export type UpdateDeviceRequest = CreateDeviceRequest;

export type Device = CreateDeviceRequest & {
  id: string,
  groupId: string,
  locationId: string
}