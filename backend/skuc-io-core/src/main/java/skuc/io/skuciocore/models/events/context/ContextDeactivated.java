package skuc.io.skuciocore.models.events.context;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import skuc.io.skuciocore.utils.JsonDateDeserializer;
import skuc.io.skuciocore.utils.JsonDateSerializer;

public class ContextDeactivated extends ContextEvent {
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(using = JsonDateDeserializer.class)
  private LocalDateTime from;

  public ContextDeactivated() {
    super();
  }

  public ContextDeactivated(String contextId, LocalDateTime from) {
    super(UUID.randomUUID().toString(), contextId);
    this.from = from;
  }

  public ContextDeactivated(String id, String contextId, LocalDateTime createdAt, LocalDateTime occuredAt) {
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
