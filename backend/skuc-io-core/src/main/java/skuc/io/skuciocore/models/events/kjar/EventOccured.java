package skuc.io.skuciocore.models.events.kjar;

import java.time.LocalDateTime;

import skuc.io.skuciocore.models.events.BaseEvent;

public class EventOccured extends BaseEvent {
  private String name;
  private boolean processedByActivator;

  public EventOccured() {
    super();
  }

  public EventOccured(String name) {
    super();
    this.name = name;
  }

  public EventOccured(String id, String deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String name) {
    super(id, deviceId, createdAt, occuredAt);
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getProcessedByActivator() {
    return this.processedByActivator;
  }

  public void setProcessedByActivator(boolean processedByActivator) {
    this.processedByActivator = processedByActivator;
  }

}
