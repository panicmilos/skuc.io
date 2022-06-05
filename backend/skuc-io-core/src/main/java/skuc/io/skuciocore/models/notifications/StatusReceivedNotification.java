package skuc.io.skuciocore.models.notifications;

public class StatusReceivedNotification extends Notification {
  private String deviceId;
  private String deviceType;
  private String value;

  public StatusReceivedNotification() {
  }

  public StatusReceivedNotification(String deviceId, String deviceType, String value, String groupId) {
    super("StatusReceived", groupId);
    this.deviceId = deviceId;
    this.deviceType = deviceType;
    this.value = value;
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getDeviceType() {
    return this.deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
