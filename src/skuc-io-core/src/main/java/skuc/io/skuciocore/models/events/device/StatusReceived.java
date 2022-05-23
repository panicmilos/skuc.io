package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;
import java.util.UUID;

public class StatusReceived extends DeviceEvent {
  private float value;

  public StatusReceived(UUID id, UUID deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String deviceType, float value) {
    super(id, deviceId, createdAt, occuredAt, deviceType);
    this.value = value;
  }

  public StatusReceived(UUID id, UUID deviceId, String deviceType) {
    super(id, deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType);
  }

  public UUID getDeviceId() {
    return this.getStreamId();
  }

  public float getValue() {
    return this.value;
  }

  public void setValue(float value) {
    this.value = value;
  }

}
