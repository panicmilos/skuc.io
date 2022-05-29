package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;
import java.util.UUID;

public class ValueReceived extends DeviceEvent {
  private String paramName;
  private float value;

  public ValueReceived(UUID id, UUID deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String deviceType, String paramName, float value) {
    super(id, deviceId, createdAt, occuredAt, deviceType);
    this.paramName = paramName;
    this.value = value;
  }

  public ValueReceived(UUID id, UUID deviceId, String deviceType) {
    super(id, deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType);
  }

  public UUID getDeviceId() {
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

}
