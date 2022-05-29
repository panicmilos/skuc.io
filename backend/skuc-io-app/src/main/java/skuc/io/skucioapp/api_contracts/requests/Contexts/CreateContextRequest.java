package skuc.io.skucioapp.api_contracts.requests.Contexts;

import skuc.io.skuciocore.models.csm.configuration.Configuration;

public class CreateContextRequest {
  private String name;
  private Configuration configuration;

  public CreateContextRequest() {
  }

  public CreateContextRequest(String name, Configuration configuration) {
    this.name = name;
    this.configuration = configuration;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }
  
}
