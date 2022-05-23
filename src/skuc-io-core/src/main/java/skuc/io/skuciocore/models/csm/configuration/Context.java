package skuc.io.skuciocore.models.csm.configuration;

import java.util.UUID;

import skuc.io.skuciocore.models.csm.BaseCsm;

public class Context extends BaseCsm {
  private String name;
  private UUID configurationId;

  public Context() {
  }

  public Context(String name, UUID configurationId) {
    this.name = name;
    this.configurationId = configurationId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getConfigurationId() {
    return this.configurationId;
  }

  public void setConfigurationId(UUID configurationId) {
    this.configurationId = configurationId;
  }

}