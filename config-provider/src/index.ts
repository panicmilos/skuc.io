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
        frequency: 5,
        filter: `function finalFilter() { return true }`,
        type: 'value'
      }
    ];
  }

}

const nimble = new Nimble().addLocalService(ConfigService);

const app = new NimblyApi().from(nimble);

const PORT = 3030;
app.listen(PORT, () => console.log(`Listening on ${PORT}`));