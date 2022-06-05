export type RegistryValue = {
  timestamp: Date;
  value: any;
};

export type Registry = {
  [key: string]: RegistryValue;
};

export type Configuration = {
  id: string;
  frequency: number; // seconds
  filter: string; // JS code
  type: "status" | "value";
};

export type ConfigurationAction = Configuration & {
  delete: boolean;
};

export interface ICanProvideConfiguration {
  onConfigure(configHandler: (configurations: Configuration[]) => any): void;
}

export interface ICanStoreValuesAndStatuses {
  setValue(topic: string, value: any): void;
  setStatus(topic: string, status: any): void;
}

export interface ICanProvideValuesAndStatuses {
  getValue(topic: string): RegistryValue;
  getStatus(topic: string): RegistryValue;
  getTopics(): string[];
}

export interface ICanBeInitializedAndDisposed {
  init(): void;
  dispose(): void;
}

export interface ICanSendToCloud {
  sendData(data: any): void;
  sendStatus(status: any): void;
}

export type EdgeGatewayParams = {
  type?: "hub" | "local";
  apiUrl: string;
  mqttBrokerUrl: string;
  mqttConfigurationTopic: string;
  cloudSender: ICanSendToCloud;
};

export type ConfigurationProviderParams = {
  mqttUrl: string;
  apiUrl: string;
  mqttConfigurationTopic: string;
}

export class NotInitialized extends Error {
  public name: string = 'NotInitialized';
}