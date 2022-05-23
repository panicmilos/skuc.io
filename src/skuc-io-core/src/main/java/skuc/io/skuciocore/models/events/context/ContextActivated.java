package skuc.io.skuciocore.models.events.context;

import java.time.LocalDateTime;
import java.util.UUID;

public class ContextActivated extends ContextEvent {
  private LocalDateTime from;

  public ContextActivated(UUID contextId, LocalDateTime from) {
    super(UUID.randomUUID(), contextId);
    this.from = from;
  }

  public ContextActivated(UUID id, UUID contextId, LocalDateTime createdAt, LocalDateTime occuredAt) {
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
