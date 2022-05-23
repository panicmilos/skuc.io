package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;
import java.util.UUID;

import skuc.io.skuciocore.models.events.BaseEvent;

public class DeviceEvent extends BaseEvent {
  private String deviceType;

  public DeviceEvent(UUID id, UUID deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String deviceType) {
    super(id, deviceId, createdAt, occuredAt);
    this.deviceType = deviceType;
  }

  public DeviceEvent(UUID id, UUID deviceId) {
    super(id, deviceId, LocalDateTime.now(), LocalDateTime.now());
  }

  public UUID getDeviceId() {
    return this.getStreamId();
  }

  public String getDeviceType() {
    return this.deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

}
