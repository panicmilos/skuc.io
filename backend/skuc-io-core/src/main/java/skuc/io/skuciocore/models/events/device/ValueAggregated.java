package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;

public class ValueAggregated extends DeviceEvent {
  private String paramName;
  private int resolution;
  private Aggregate aggregate;

  public ValueAggregated(String id, String deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String deviceType,
      String paramName, int resolution, Aggregate aggregate) {
    super(id, deviceId, createdAt, occuredAt, deviceType);
    this.paramName = paramName;
    this.resolution = resolution;
    this.aggregate = aggregate;
  }

  public String getDeviceId() {
    return this.getStreamId();
  }

  public String getParamName() {
    return this.paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public Aggregate getAggregate() {
    return this.aggregate;
  }

  public void setAggregate(Aggregate aggregate) {
    this.aggregate = aggregate;
  }

  public int getResolution() {
    return this.resolution;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

}
