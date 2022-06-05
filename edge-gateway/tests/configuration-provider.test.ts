import axios from 'axios';
import { Configuration, ConfigurationProvider, ConfigurationProviderParams, ICanProvideConfiguration } from '../src'

const NEW_CFG_TIMEOUT = 1000;

jest.mock("axios");
jest.mock("isobarot-client", () => ({
  MqttClient: class {
    subscribe(topic: string, cb: (config: Configuration[]) => any) {
      setTimeout(() => cb([{id: '2'} as any]), NEW_CFG_TIMEOUT);
    }
  }
}));

const configProviderParams: ConfigurationProviderParams = {
  mqttUrl: '',
  apiUrl: '',
  mqttConfigurationTopic: ''
}

describe('ConfigurationProvider', () => {

  it('should create new instance', () => {
    const _ = new ConfigurationProvider(configProviderParams);
  });

  it('should receive initial configuration', async () => {
    // @ts-ignore
    axios.get.mockResolvedValueOnce({
      data: [
        {id: '1'}
      ]
    });

    const config = new ConfigurationProvider(configProviderParams);

    await new Promise(r => {
      config.onConfigure((configurations: Configuration[]) => {
        expect(configurations).toHaveLength(1);
        
        r(null);
      });
    });

  });

  it('should receive initial configuration', async () => {
    jest.useFakeTimers();
    // @ts-ignore
    axios.get.mockResolvedValue({
      data: [
        {id: '1'}
      ]
    });

    const config = new ConfigurationProvider(configProviderParams);

    config.onConfigure(() => {});
    jest.advanceTimersByTime(NEW_CFG_TIMEOUT);

    await new Promise(r => {
      config.onConfigure((configurations: Configuration[]) => {
        expect(configurations).toHaveLength(2);
        r(null);
      });
    });
  });
  
});