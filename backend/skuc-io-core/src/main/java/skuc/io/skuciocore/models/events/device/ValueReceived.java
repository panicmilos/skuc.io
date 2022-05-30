package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;
import java.util.UUID;

public class ValueReceived extends DeviceEvent {
  private String paramName;
  private float value;
  private boolean isProcessed;

  public ValueReceived() {
    super();
    super.setId(UUID.randomUUID().toString());
  }

  public ValueReceived(String id, String deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String deviceType, String paramName, float value, boolean isProcessed) {
    super(id, deviceId, createdAt, occuredAt, deviceType);
    this.paramName = paramName;
    this.value = value;
    this.isProcessed = isProcessed;
  }

  public ValueReceived(String id, String deviceId, String deviceType) {
    super(id, deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType);
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

  public float getValue() {
    return this.value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public boolean isProcessed() {
    return this.isProcessed;
  }

  public void setIsProcessed(boolean isProcessed) {
    this.isProcessed = isProcessed;
  }

}
