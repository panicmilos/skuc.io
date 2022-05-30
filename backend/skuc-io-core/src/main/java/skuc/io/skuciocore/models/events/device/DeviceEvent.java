package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;

import skuc.io.skuciocore.models.events.BaseEvent;

public class DeviceEvent extends BaseEvent {
  private String deviceType;

  public DeviceEvent() {
    super();
  }

  public DeviceEvent(String id, String deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String deviceType) {
    super(id, deviceId, createdAt, occuredAt);
    this.deviceType = deviceType;
  }

  public DeviceEvent(String id, String deviceId) {
    super(id, deviceId, LocalDateTime.now(), LocalDateTime.now());
  }

  public String getDeviceId() {
    return this.getStreamId();
  }

  public String getDeviceType() {
    return this.deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

}
