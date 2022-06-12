package skuc.io.skuciocore.models.reports;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import skuc.io.skuciocore.utils.JsonDateDeserializer;
import skuc.io.skuciocore.utils.JsonDateSerializer;

public class LocalDateTimePeriod {
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(using = JsonDateDeserializer.class)
  private LocalDateTime from;
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(using = JsonDateDeserializer.class)
  private LocalDateTime to;

  public LocalDateTimePeriod() {
    super();
  }

  public LocalDateTimePeriod(LocalDateTime from, LocalDateTime to) {
    this.from = from;
    this.to = to;
  }

  public LocalDateTime getFrom() {
    return this.from;
  }

  public void setFrom(LocalDateTime from) {
    this.from = from;
  }

  public LocalDateTime getTo() {
    return this.to;
  }

  public void setTo(LocalDateTime to) {
    this.to = to;
  }

}
