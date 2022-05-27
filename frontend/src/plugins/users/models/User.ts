export type CreateUserRequest = {
  email: string,
  password: string,
  fullName: string,
  address: string,
  phoneNumber: string,
  role: string,
}

export type UpdateUserRequest = {
  fullName: string,
  address: string,
  phoneNumber: string,
}

export type User = CreateUserRequest & {
  id: string,
  groupId: string
}