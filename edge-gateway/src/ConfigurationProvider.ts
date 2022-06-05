import axios from "axios";
import { MqttClient } from "isobarot-client";
import { Configuration, ConfigurationAction, ConfigurationProviderParams, ICanProvideConfiguration } from "./common";

export class ConfigurationProvider implements ICanProvideConfiguration {
  private configurations: Configuration[];
  private configHandlers: ((configurations: Configuration[]) => any)[] = [];

  constructor(private params: ConfigurationProviderParams) {
    new MqttClient(params.mqttUrl).subscribe(params.mqttConfigurationTopic, async (configurations: ConfigurationAction[]) => {
      if(!this.configurations) await this.fetchConfigurations();
      this.configurations = [
        ...this.configurations.filter(c => !configurations.find(co => co.id === c.id)),
        ...configurations.filter(ca => !ca.delete)
      ];
      this.configHandlers.forEach(ch => ch(this.configurations ?? []));
    });
  }

  public async onConfigure(configHandler: (configurations: Configuration[]) => any) {
    this.configHandlers.push(configHandler);
    if(!this.configurations) await this.fetchConfigurations();
    configHandler(this.configurations ?? []);
  }

  private async fetchConfigurations() {
    try {
      const resp = await axios.get(this.params.apiUrl);
      if(resp?.data) {
        this.configurations = resp.data;
      }
    } catch (err) {
      console.log(`Failed to fetch configurations ${err}.`)
    }
  }

}
