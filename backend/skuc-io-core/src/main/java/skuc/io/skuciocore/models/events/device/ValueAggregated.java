package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class ValueAggregated extends DeviceEvent {
  private String paramName;
  private int resolution;
  private Aggregate aggregate;
  private boolean isProcessed;
  private Date timeStamp = new Date();

  public ValueAggregated(String id, String deviceId, LocalDateTime createdAt, LocalDateTime occuredAt,
      String deviceType,
      String paramName, int resolution, Aggregate aggregate) {
    super(id, deviceId, createdAt, occuredAt, deviceType);
    this.paramName = paramName;
    this.resolution = resolution;
    this.aggregate = aggregate;
  }

  public ValueAggregated(String deviceId, String deviceType, String paramName, int resolution, Aggregate aggregate) {
    super(UUID.randomUUID().toString(), deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType);
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

  public boolean getIsProcessed() {
    return this.isProcessed;
  }

  public void setIsProcessed(boolean isProcessed) {
    this.isProcessed = isProcessed;
  }

  public Date getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

}
