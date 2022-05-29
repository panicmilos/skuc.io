package skuc.io.skucioapp.api_contracts.requests.Contexts;

import skuc.io.skuciocore.models.csm.configuration.Configuration;

public class UpdateContextRequest {
  private Configuration configuration;

  public UpdateContextRequest() {
  }

  public UpdateContextRequest(Configuration configuration) {
    this.configuration = configuration;
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

}
