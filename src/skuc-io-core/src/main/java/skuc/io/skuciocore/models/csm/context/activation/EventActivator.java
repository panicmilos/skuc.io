package skuc.io.skuciocore.models.csm.context.activation;

import skuc.io.skuciocore.models.csm.context.BaseContextCsm;

public class EventActivator extends BaseContextCsm {
  private String eventType;

  public EventActivator() {
  }

  public EventActivator(String eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return this.eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

}
