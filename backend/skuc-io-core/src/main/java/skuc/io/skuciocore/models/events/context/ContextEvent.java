package skuc.io.skuciocore.models.events.context;

import java.time.LocalDateTime;
import java.util.UUID;

import skuc.io.skuciocore.models.events.BaseEvent;

public class ContextEvent extends BaseEvent {

  public ContextEvent(UUID id, UUID contextId, LocalDateTime createdAt, LocalDateTime occuredAt) {
    super(id, contextId, createdAt, occuredAt);
  }

  public ContextEvent(UUID id, UUID contextId) {
    super(id, contextId, LocalDateTime.now(), LocalDateTime.now());
  }

  public UUID getContextId() {
    return this.getStreamId();
  }

}
