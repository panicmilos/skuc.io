package skuc.io.skuciocore.models.events.kjar;

public class ContextDeleted {

  public String contextId;

  public ContextDeleted() {
  }

  public ContextDeleted(String contextId) {
    this.contextId = contextId;
  }

  public String getContextId() {
    return this.contextId;
  }

  public void setContextId(String contextId) {
    this.contextId = contextId;
  }

}
