export type Configuration = {
  thresholdConfiguration: ThresholdConfiguration,
  statusConfiguration: StatusConfiguration
}

export type ThresholdConfiguration = {
  [key: string]: ThresholdsConfig
}

export type StatusConfiguration = {
  [key: string]: StatusConfig
}

export type ThresholdsConfig = {
  min: number,
  max: number
}

export type StatusConfig = {
  expectedValue: string
}