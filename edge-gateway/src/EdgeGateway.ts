import { ICanStoreValuesAndStatuses, ICanProvideValuesAndStatuses, Registry, RegistryValue, ICanProvideConfiguration, ICanBeInitializedAndDisposed, NotInitialized, EdgeGatewayParams } from "./common";
import { ConfigurationProvider } from "./ConfigurationProvider";
import { SynchronizationHandler } from "./SynchronizationHandler";

export class EdgeGateway implements ICanStoreValuesAndStatuses, ICanBeInitializedAndDisposed {
  private initialized: boolean = false;
  private registryHandler: ICanStoreValuesAndStatuses;
  private valuesAndStatusesProvider: ICanProvideValuesAndStatuses;
  private configurationProvider: ICanProvideConfiguration;
  private syncHandlers: ICanBeInitializedAndDisposed[] = [];

  constructor(private params: EdgeGatewayParams) {}

  public init() {
    if (this.params.type === "hub") {
      // TODO: Implement hub logic
    } else if (this.params.type === "local") {
      this.initLocal();
    }
    this.initConfigAndSync();
    this.initialized = true;
  }

  public dispose(): void {
    this.disposeSyncHandlers()
  }

  public setValue(topic: string, value: any): void {
    if(!this.initialized)
      throw new NotInitialized("EdgeGateway must be initialized before usage, init() method not called!");
    this.registryHandler.setValue(topic, value);
  }

  public setStatus(topic: string, status: any): void {
    if(!this.initialized)
      throw new NotInitialized("EdgeGateway must be initialized before usage, init() method not called!");
    this.registryHandler.setStatus(topic, status);
  }

  private initLocal() {
    const registry = new RegistryHandler();
    this.registryHandler = registry;
    this.valuesAndStatusesProvider = registry;
  }

  private initConfigAndSync() {
    this.configurationProvider = new ConfigurationProvider({
      apiUrl: this.params.apiUrl,
      mqttUrl: this.params.mqttBrokerUrl,
      mqttConfigurationTopic: this.params.mqttConfigurationTopic
    });
    this.configurationProvider.onConfigure((configs) => {
      this.disposeSyncHandlers();
      this.syncHandlers = configs.map(config => new SynchronizationHandler(config, this.valuesAndStatusesProvider, this.params.cloudSender));
      this.syncHandlers.forEach(syncHandler => syncHandler.init());
    });
  }

  private disposeSyncHandlers() {
    this.syncHandlers.forEach(syncHandler => syncHandler.dispose());
  }
}

class RegistryHandler
  implements ICanStoreValuesAndStatuses, ICanProvideValuesAndStatuses
{
  private valueRegistry: Registry = {};
  private statusRegistry: Registry = {};

  public setValue(topic: string, value: any): void {
    this.valueRegistry[topic] = {
      timestamp: new Date(),
      value,
    };
  }

  public setStatus(topic: string, status: any): void {
    this.statusRegistry[topic] = {
      timestamp: new Date(),
      value: status,
    };
  }

  public getValue(topic: string): RegistryValue | undefined {
    return this.valueRegistry[topic];
  }

  public getStatus(topic: string): RegistryValue | undefined {
    return this.statusRegistry[topic];
  }

  public getTopics(): string[] {
    return [...Object.keys(this.valueRegistry), ...Object.keys(this.statusRegistry)];
  }
}
