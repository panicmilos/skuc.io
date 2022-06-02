package skuc.io.skuciocore.models.events.kjar;


public class DeactivateContextById  {
  private String contextId;

  public DeactivateContextById(String contextId) {
    this.contextId = contextId;
  }

  public String getContextId() {
    return this.contextId;
  }

  public void setContextId(String contextId) {
    this.contextId = contextId;
  }

}
