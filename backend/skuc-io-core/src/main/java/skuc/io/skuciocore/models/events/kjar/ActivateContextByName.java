package skuc.io.skuciocore.models.events.kjar;

public class ActivateContextByName {
  private String contextName;

  public ActivateContextByName(String contextName) {
    this.contextName = contextName;
  }

  public String getContextName() {
    return this.contextName;
  }

  public void setContextName(String contextName) {
    this.contextName = contextName;
  }

}
