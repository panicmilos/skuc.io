package skuc.io.skuciocore.models.events.context;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import skuc.io.skuciocore.utils.JsonDateDeserializer;
import skuc.io.skuciocore.utils.JsonDateSerializer;

public class ContextActivated extends ContextEvent {
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(using = JsonDateDeserializer.class)
  private LocalDateTime from;

  public ContextActivated() {
    super();
  }

  public ContextActivated(String contextId, String locationId, LocalDateTime from) {
    super(UUID.randomUUID().toString(), contextId, locationId);
    this.from = from;
  }

  public ContextActivated(String id, String contextId, String locationId, LocalDateTime createdAt, LocalDateTime occuredAt) {
    super(id, contextId, locationId, createdAt, occuredAt);
    this.from = occuredAt;
  }

  public LocalDateTime getFrom() {
    return this.from;
  }

  public void setFrom(LocalDateTime from) {
    this.from = from;
  }

}
