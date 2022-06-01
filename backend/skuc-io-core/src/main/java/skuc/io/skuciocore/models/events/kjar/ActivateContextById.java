package skuc.io.skuciocore.models.events.kjar;


public class ActivateContextById  {
  private String contextId;

  public ActivateContextById(String contextId) {
    this.contextId = contextId;
  }

  public String getContextId() {
    return this.contextId;
  }

  public void setContextId(String contextId) {
    this.contextId = contextId;
  }

}
