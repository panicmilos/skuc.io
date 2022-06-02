package skuc.io.skuciocore.models.events.kjar;

public class DeactivateContextByName {
  private String contextName;

  public DeactivateContextByName(String contextName) {
    this.contextName = contextName;
  }

  public String getContextName() {
    return this.contextName;
  }

  public void setContextName(String contextName) {
    this.contextName = contextName;
  }

}
