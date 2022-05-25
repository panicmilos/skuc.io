export type CreateLocationRequest = {
  name: string,
  lng: number,
  lat: number
}

export type UpdateLocationRequest = CreateLocationRequest;

export type Location = CreateLocationRequest & {
  id: string,
  groupId: string
}