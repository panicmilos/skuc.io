package skuc.io.skuciocore.models.events;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseEvent {
  private UUID id = UUID.randomUUID();
  private UUID streamId = UUID.randomUUID();
  private String type = this.getClass().getSimpleName();
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime occuredAt = LocalDateTime.now();

  public BaseEvent() {
  }

  public BaseEvent(UUID id, UUID streamId, LocalDateTime createdAt, LocalDateTime occuredAt) {
    this.id = id;
    this.streamId = streamId;
    this.createdAt = createdAt;
    this.occuredAt = occuredAt;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getOccuredAt() {
    return this.occuredAt;
  }

  public void setOccuredAt(LocalDateTime occuredAt) {
    this.occuredAt = occuredAt;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public UUID getStreamId() {
    return this.streamId;
  }

  public void setStreamId(UUID streamId) {
    this.streamId = streamId;
  }

}
