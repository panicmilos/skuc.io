package skuc.io.skuciocore.models.reports;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import skuc.io.skuciocore.utils.JsonDateDeserializer;
import skuc.io.skuciocore.utils.JsonDateSerializer;

public class ReportResultValue {
  private Object value;
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(using = JsonDateDeserializer.class)
  private LocalDateTime createdAt;

  public ReportResultValue() {
    super();
  }

  public ReportResultValue(Object value, LocalDateTime createdAt) {
    this.value = value;
    this.createdAt = createdAt;
  }

  public Object getValue() {
    return this.value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
