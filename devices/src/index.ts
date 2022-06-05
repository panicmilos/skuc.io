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
  sendData(data: any): void {
    const { value, deviceType, deviceId } = data.value;
    // axios.post(`http://localhost:8080/events/statuses`, {
    //   value,
    //   deviceType,
    //   streamId: deviceId
    // })
    console.log('VALUE', value, deviceType, deviceId);
  }
  sendStatus(data: any): void {
    const { value, deviceType, deviceId } = data.value;
    // axios.post(`http://localhost:8080/events/statuses`, {
    //   value,
    //   deviceType,
    //   streamId: deviceId
    // })
    console.log('STATUS', value, deviceType, deviceId);
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

const temperature: SeqElement[] = [
  { timeout: 3, value: { value: 18, deviceType: "temperature", deviceId: "temperatureid" } },
  { timeout: 3, value: { value: 19, deviceType: "temperature", deviceId: "temperatureid" } },
  { timeout: 3, value: { value: 18.5, deviceType: "temperature", deviceId: "temperatureid" } },
  { timeout: 3, value: { value: 20, deviceType: "temperature", deviceId: "temperatureid" } },
  { timeout: 3, value: { value: 22, deviceType: "temperature", deviceId: "temperatureid" } },
];

const co2: RandomSeqElement[] = [
  {
    probability: 5,
    value: { value: 0.3, deviceType: "co2", deviceId: "co2id" },
  },
  {
    probability: 2,
    value: { value: 0.5, deviceType: "co2", deviceId: "co2id" },
  },
  {
    probability: 1,
    value: { value: 0.7, deviceType: "co2", deviceId: "co2id" },
  },
];

const humidity: RandomSeqElement[] = [
  {
    probability: 10,
    value: { value: 0.4, deviceType: "humidity", deviceId: "humidityid" },
  },
  {
    probability: 5,
    value: { value: 0.5, deviceType: "humidity", deviceId: "humidityid" },
  },
  {
    probability: 3,
    value: { value: 0.6, deviceType: "humidity", deviceId: "humidityid" },
  },
  {
    probability: 1,
    value: { value: 0.7, deviceType: "humidity", deviceId: "humidityid" },
  },
  {
    probability: 1,
    value: { value: 0.2, deviceType: "humidity", deviceId: "humidityid" },
  },
];

const ac: SeqElement[] = [
  { timeout: 30, value: { value: "on", deviceType: "ac", deviceId: "acid" }, type: "status" },
  { timeout: 30, value: { value: "off", deviceType: "ac", deviceId: "acid" }, type: "status" },
];

const windows: SeqElement[] = [
  { timeout: 30, value: { value: "opened", deviceType: "windows", deviceId: "windowsid" }, type: "status" },
  { timeout: 30, value: { value: "closed", deviceType: "windows", deviceId: "windowsid" }, type: "status" },
];

const sprinkler: SeqElement[] = [
  { timeout: 999999, value: { value: "off", deviceType: "sprinkler", deviceId: "sprinklerid" }, type: "status" },
  { timeout: 30, value: { value: "on", deviceType: "sprinkler", deviceId: "sprinklerid" }, type: "status" },
];

const heating: SeqElement[] = [
  { timeout: 30, value: { value: "on", deviceType: "heating", deviceId: "heatingid" }, type: "status" },
  { timeout: 30, value: { value: "off", deviceType: "heating", deviceId: "heatingid" }, type: "status" },
];

const boiler: SeqElement[] = [
  { timeout: 30, value: { value: "on", deviceType: "boiler", deviceId: "boilerid" }, type: "status" },
  { timeout: 30, value: { value: "off", deviceType: "boiler", deviceId: "boilerid" }, type: "status" },
];

const lights: SeqElement[] = [
  { timeout: 30, value: { value: "on", deviceType: "lights", deviceId: "lightsid" }, type: "status" },
  { timeout: 30, value: { value: "off", deviceType: "lights", deviceId: "lightsid" }, type: "status" },
];

const washing: SeqElement[] = [
  { timeout: 30, value: { value: "on", deviceType: "washing", deviceId: "washingid" }, type: "status" },
  { timeout: 30, value: { value: "off", deviceType: "washing", deviceId: "washingid" }, type: "status" },
];

const camera: SeqElement[] = [
  { timeout: 30, value: { value: "hasActivity", deviceType: "camera", deviceId: "cameraid" }, type: "status" },
  { timeout: 30, value: { value: "noActivity", deviceType: "camera", deviceId: "cameraid" }, type: "status" },
];

const movement: SeqElement[] = [
  { timeout: 30, value: { value: 0, deviceType: "movement", deviceId: "movementid" } },
  { timeout: 30, value: { value: 0.1, deviceType: "movement", deviceId: "movementid" } },
  { timeout: 30, value: { value: 0.7, deviceType: "movement", deviceId: "movementid" } },
];

const sound: SeqElement[] = [
  { timeout: 30, value: { value: 0, deviceType: "sound", deviceId: "soundid" } },
  { timeout: 30, value: { value: 0.1, deviceType: "sound", deviceId: "soundid" } },
  { timeout: 30, value: { value: 0.7, deviceType: "sound", deviceId: "soundid" } },
];

const pet: SeqElement[] = [
  { timeout: 5, value: { value: 25, deviceType: "pet", deviceId: "petid" } },
  { timeout: 5, value: { value: 30, deviceType: "pet", deviceId: "petid" } },
  { timeout: 5, value: { value: 50, deviceType: "pet", deviceId: "petid" } },
  { timeout: 5, value: { value: 55, deviceType: "pet", deviceId: "petid" } },
  { timeout: 5, value: { value: 70, deviceType: "pet", deviceId: "petid" } },
  { timeout: 5, value: { value: 80, deviceType: "pet", deviceId: "petid" } },
  { timeout: 5, value: { value: 70, deviceType: "pet", deviceId: "petid" } },
  { timeout: 5, value: { value: 55, deviceType: "pet", deviceId: "petid" } },
  { timeout: 5, value: { value: 35, deviceType: "pet", deviceId: "petid" } },
  { timeout: 5, value: { value: 25, deviceType: "pet", deviceId: "petid" } },
];

const bathroom: SeqElement[] = [
  { timeout: 30, value: { value: 0, deviceType: "bathroom", deviceId: "bathroomid" } },
  { timeout: 30, value: { value: 1, deviceType: "bathroom", deviceId: "bathroomid" } },
];

const simulators: ICanBeInitializedAndDisposed[] = [
  new RandomSimulator(gw, "co2", co2, 5_000),
  new RandomSimulator(gw, "humidity", humidity, 5_000),
  new SeqSimulator(gw, "temperature", temperature),
  new SeqSimulator(gw, "ac", ac),
  new SeqSimulator(gw, "windows", windows),
  new SeqSimulator(gw, "sprinkler", sprinkler),
  new SeqSimulator(gw, "heating", heating),
  new SeqSimulator(gw, "boiler", boiler),
  new SeqSimulator(gw, "lights", lights),
  new SeqSimulator(gw, "washing", washing),
  new SeqSimulator(gw, "camera", camera),
  new SeqSimulator(gw, "movement", movement),
  new SeqSimulator(gw, "sound", sound),
  new SeqSimulator(gw, "pet", pet),
  new SeqSimulator(gw, "bathroom", bathroom),
];

simulators.forEach((s) => s.init());
