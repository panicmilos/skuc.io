package skuc.io.skuciocore.models.csm.configuration;

import skuc.io.skuciocore.models.csm.BaseCsm;

public class Context extends BaseCsm {
  private String name;
  private String groupId;
  private Configuration configuration;

  public Context() {
  }

  public Context(String name, String groupId, Configuration configuration) {
    this.name = name;
    this.groupId = groupId;
    this.configuration = configuration;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public Configuration getConfiguration() {
    return this.configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

}