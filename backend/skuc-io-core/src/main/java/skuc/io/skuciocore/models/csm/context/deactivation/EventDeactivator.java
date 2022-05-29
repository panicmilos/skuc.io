package skuc.io.skuciocore.models.csm.context.deactivation;

import skuc.io.skuciocore.models.csm.context.BaseContextCsm;

public class EventDeactivator extends BaseContextCsm {
  private String eventType;

  public EventDeactivator() {
  }

  public EventDeactivator(String eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return this.eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

}
