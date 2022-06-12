
export type CreatePredefinedReport = {
  name: string
  type: string
  resolution: number
  paramFilters: any
}

export type UpdatePredefinedReport = {
  name: string
  resolution: number
  paramFilters: any
}

export type PredefinedReport = CreatePredefinedReport & {
  id: string
  locationId: string
  groupId: string
}