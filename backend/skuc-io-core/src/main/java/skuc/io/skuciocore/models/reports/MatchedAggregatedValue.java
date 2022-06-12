package skuc.io.skuciocore.models.reports;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import skuc.io.skuciocore.utils.JsonDateDeserializer;
import skuc.io.skuciocore.utils.JsonDateSerializer;

public class MatchedAggregatedValue {
  private String paramName;
  private String algorithm;
  private Object value;
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(using = JsonDateDeserializer.class)
  private LocalDateTime createdAt;

  public MatchedAggregatedValue() {
    super();
  }

  public MatchedAggregatedValue(String paramName, String algorithm, Object value, LocalDateTime createdAt) {
    this.paramName = paramName;
    this.algorithm = algorithm;
    this.value = value;
    this.createdAt = createdAt;
  }

  public String getParamName() {
    return this.paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public String getAlgorithm() {
    return this.algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
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
