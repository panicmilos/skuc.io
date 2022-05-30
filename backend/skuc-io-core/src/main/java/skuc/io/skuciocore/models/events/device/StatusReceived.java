package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;

public class StatusReceived extends DeviceEvent {
  private float value;

  public StatusReceived(String id, String deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String deviceType, float value) {
    super(id, deviceId, createdAt, occuredAt, deviceType);
    this.value = value;
  }

  public StatusReceived(String id, String deviceId, String deviceType) {
    super(id, deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType);
  }

  public String getDeviceId() {
    return this.getStreamId();
  }

  public float getValue() {
    return this.value;
  }

  public void setValue(float value) {
    this.value = value;
  }

}
