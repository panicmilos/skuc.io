package skuc.io.skuciocore.models.events;

import java.time.LocalDateTime;
import java.util.UUID;

import org.kie.api.definition.type.Position;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import skuc.io.skuciocore.utils.JsonDateDeserializer;
import skuc.io.skuciocore.utils.JsonDateSerializer;

public class BaseEvent {
  @Position(0)
  private String id = UUID.randomUUID().toString();
  private String streamId = UUID.randomUUID().toString();
  private String type = this.getClass().getSimpleName();
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(using = JsonDateDeserializer.class)
  private LocalDateTime createdAt = LocalDateTime.now();
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(using = JsonDateDeserializer.class)
  private LocalDateTime occuredAt = LocalDateTime.now();

  public BaseEvent() {
  }

  public BaseEvent(String id, String streamId) {
    this.id = id;
    this.streamId = streamId;
  }

  public BaseEvent(String id, String streamId, LocalDateTime createdAt, LocalDateTime occuredAt) {
    this.id = id;
    this.streamId = streamId;
    this.createdAt = createdAt;
    this.occuredAt = occuredAt;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
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

  public String getStreamId() {
    return this.streamId;
  }

  public void setStreamId(String streamId) {
    this.streamId = streamId;
  }

}
