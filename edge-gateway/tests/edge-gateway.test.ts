import { EdgeGateway, EdgeGatewayParams } from '../src'

jest.mock("axios");

const configure = jest.fn();
const init = jest.fn();
const dispose = jest.fn();

const NUMBER_OF_CONFIGS = 5;

jest.mock('../src/ConfigurationProvider', () => ({
  ConfigurationProvider: class {
    onConfigure(cb: (configs: any[]) => any) {
      configure();
      cb(Array.from({ length: 5 }).map(() => ({})));
    }
  }
}));

jest.mock('../src/SynchronizationHandler', () => ({
  SynchronizationHandler: class {
    init() { init() }
    dispose() { dispose() }
  }
}));

const gwParams: EdgeGatewayParams = { type: 'local', apiUrl: '', mqttBrokerUrl: '', mqttConfigurationTopic: '', cloudSender: null };

describe('EdgeGateway', () => {

  it('should create a local instance', () => {
    const _ = new EdgeGateway(gwParams);
  });

  it('should create a hub instance', () => {
    const _ = new EdgeGateway({ type: 'hub', apiUrl: '', mqttBrokerUrl: '', mqttConfigurationTopic: '', cloudSender: null });
  });

  it('should initialize', () => {
    const numberOfConfigureCalls = configure.mock.calls.length;
    const numberOfInitCalls = init.mock.calls.length;

    const gw = new EdgeGateway(gwParams);
    gw.init();

    expect(configure.mock.calls.length).toBe(numberOfConfigureCalls + 1);
    expect(init.mock.calls.length).toBe(numberOfInitCalls + NUMBER_OF_CONFIGS);
  });

  it('should dispose', () => {
    const numberOfDisposeCalls = dispose.mock.calls.length;

    const gw = new EdgeGateway(gwParams);
    gw.init();
    gw.dispose();

    expect(dispose.mock.calls.length).toBe(numberOfDisposeCalls + NUMBER_OF_CONFIGS);
  });

  it('should fail if not initialized', () => {
    const gw = new EdgeGateway(gwParams);
    try {
      gw.setValue('test.topic', 123);
      fail();
    } catch (error) {
      expect(error.name).toBe('NotInitialized');
    } finally {
      gw.dispose();
    }
  });

  it('should store a value in a registry', () => {
    const gw = new EdgeGateway(gwParams);
    gw.init();

    gw.setValue('test.topic', 123);

    gw.dispose();
  });

  it('should store a status in a registry', () => {
    const gw = new EdgeGateway(gwParams);
    gw.init();

    const internalGW: any = (gw as any).registryHandler;
    expect(internalGW.statusRegistry['test.topic']).toBeUndefined();

    gw.setStatus('test.topic', 'random-status');

    expect(internalGW.statusRegistry['test.topic']).not.toBeUndefined();

    gw.dispose();
  });
  
});