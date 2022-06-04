import axios from "axios";
import {
  EdgeGateway,
  ICanBeInitializedAndDisposed,
  ICanSendToCloud,
} from "../../edge-gateway/src";
import {
  RandomSeqElement,
  RandomSimulator,
  SeqElement,
  SeqSimulator,
} from "./Simulator";

class Sender implements ICanSendToCloud {
  sendData({value, deviceType, deviceId}: any): void {
    axios.post(`http://localhost:8080/events/statuses`, {
      value,
      deviceType,
      streamId: deviceId
    })
  }
  sendStatus(status: any): void {
    throw new Error("Method not implemented.");
  }
}

const gw = new EdgeGateway({
  apiUrl: "http://localhost:3030/api/configuration",
  mqttBrokerUrl: "isobarot.com",
  mqttConfigurationTopic: "skucio.config",
  cloudSender: new Sender(),
  type: "local",
});

gw.init();

const sequence1: RandomSeqElement[] = [
  {
    probability: 5,
    value: {
      paja: "pera",
      test: 1,
      asdfg: true,
    },
  },
];

const sequentialSequence: SeqElement[] = [
  { timeout: 3, value: { value: "test", deviceType: "test", deviceId: "21321" } },
  { timeout: 3, value: { value: "test2", deviceType: "test", deviceId: "21321" } },
];

const simulators: ICanBeInitializedAndDisposed[] = [
  // new RandomSimulator(gw, "test", sequence1, 10_000),
  new SeqSimulator(gw, "test", sequentialSequence),
];

simulators.forEach((s) => s.init());
