package skuc.io.skuciocore.models.events.context;

import java.time.LocalDateTime;
import java.util.UUID;

public class ContextDeactivated extends ContextEvent {
  private LocalDateTime from;

  public ContextDeactivated(UUID contextId, LocalDateTime from) {
    super(UUID.randomUUID(), contextId);
    this.from = from;
  }

  public ContextDeactivated(UUID id, UUID contextId, LocalDateTime createdAt, LocalDateTime occuredAt) {
    super(id, contextId, createdAt, occuredAt);
    this.from = occuredAt;
  }

  public LocalDateTime getFrom() {
    return this.from;
  }

  public void setFrom(LocalDateTime from) {
    this.from = from;
  }

}
