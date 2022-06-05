import { Controller, Get, int, Nimble, NimblyApi, ReturnsArrayOf, string } from "nimbly-api";

class Configuration {
  @string id: string;
  @int frequency: number; // seconds
  @string filter: string; // JS code
  @string type: "status" | "value";
};

@Controller('/configuration')
class ConfigService {

  @Get()
  @ReturnsArrayOf(Configuration)
  public async get(): Promise<Configuration[]> {
    return [
      {
        id: '1',
        frequency: 3,
        filter: `function finalFilter(topic) { return topic === 'temperature' }`,
        type: 'value'
      },
      {
        id: '2',
        frequency: 5,
        filter: `function finalFilter(topic) { return topic === 'co2' }`,
        type: 'value'
      },
      {
        id: '3',
        frequency: 5,
        filter: `function finalFilter(topic) { return topic === 'humidity' }`,
        type: 'value'
      },
      {
        id: '4',
        frequency: 30,
        filter: `function finalFilter(topic) { return topic === 'ac' }`,
        type: 'status'
      },
      {
        id: '5',
        frequency: 30,
        filter: `function finalFilter(topic) { return topic === 'sprinkler' }`,
        type: 'status'
      },
      {
        id: '6',
        frequency: 30,
        filter: `function finalFilter(topic) { return topic === 'heating' }`,
        type: 'status'
      },
      {
        id: '7',
        frequency: 30,
        filter: `function finalFilter(topic) { return topic === 'boiler' }`,
        type: 'status'
      },
      {
        id: '8',
        frequency: 30,
        filter: `function finalFilter(topic) { return topic === 'lights' }`,
        type: 'status'
      },
      {
        id: '9',
        frequency: 30,
        filter: `function finalFilter(topic) { return topic === 'washing' }`,
        type: 'status'
      },
      {
        id: '10',
        frequency: 30,
        filter: `function finalFilter(topic) { return topic === 'camera' }`,
        type: 'status'
      },
      {
        id: '11',
        frequency: 30,
        filter: `function finalFilter(topic) { return topic === 'movement' }`,
        type: 'value'
      },
      {
        id: '12',
        frequency: 30,
        filter: `function finalFilter(topic) { return topic === 'sound' }`,
        type: 'value'
      },
      {
        id: '13',
        frequency: 5,
        filter: `function finalFilter(topic) { return topic === 'pet' }`,
        type: 'value'
      },
      {
        id: '14',
        frequency: 30,
        filter: `function finalFilter(topic) { return topic === 'windows' }`,
        type: 'status'
      },
      {
        id: '15',
        frequency: 5,
        filter: `function finalFilter(topic) { return topic === 'bathroom' }`,
        type: 'value'
      },
    ];
  }

}

const nimble = new Nimble().addLocalService(ConfigService);

const app = new NimblyApi().from(nimble);

const PORT = 3030;
app.listen(PORT, () => console.log(`Listening on ${PORT}`));