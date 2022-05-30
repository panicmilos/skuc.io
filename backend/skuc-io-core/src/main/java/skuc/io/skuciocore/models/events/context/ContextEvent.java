package skuc.io.skuciocore.models.events.context;

import java.time.LocalDateTime;

import skuc.io.skuciocore.models.events.BaseEvent;

public class ContextEvent extends BaseEvent {

  public ContextEvent(String id, String contextId, LocalDateTime createdAt, LocalDateTime occuredAt) {
    super(id, contextId, createdAt, occuredAt);
  }

  public ContextEvent(String id, String contextId) {
    super(id, contextId, LocalDateTime.now(), LocalDateTime.now());
  }

  public String getContextId() {
    return this.getStreamId();
  }

}
