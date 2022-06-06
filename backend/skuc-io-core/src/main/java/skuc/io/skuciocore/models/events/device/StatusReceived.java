package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;

public class StatusReceived extends DeviceEvent {
  private String value;

  public StatusReceived() {
    
  }

  public StatusReceived(String id, String deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String deviceType, String value) {
    super(id, deviceId, createdAt, occuredAt, deviceType);
    this.value = value;
  }

  public StatusReceived(String id, String deviceId, String deviceType) {
    super(id, deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType);
  }

  public StatusReceived(String id, String deviceId, String deviceType, String value) {
    super(id, deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType);
    this.value = value;
  }

  public String getDeviceId() {
    return this.getStreamId();
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
