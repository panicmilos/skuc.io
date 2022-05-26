import { Configuration } from "./Configuration"

export type CreateContextReqest = {
  name: string
  configuration: Configuration
}

export type UpdateContextRequest = {
  configuration: Configuration
}

export type Context = CreateContextReqest & {
  id: string,
  groupId: string
}

