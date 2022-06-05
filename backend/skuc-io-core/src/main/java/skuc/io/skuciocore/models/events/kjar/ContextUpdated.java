package skuc.io.skuciocore.models.events.kjar;

public class ContextUpdated {
  public String contextId;

  public ContextUpdated() {
  }

  public ContextUpdated(String contextId) {
    this.contextId = contextId;
  }

  public String getContextId() {
    return this.contextId;
  }

  public void setContextId(String contextId) {
    this.contextId = contextId;
  }
}
