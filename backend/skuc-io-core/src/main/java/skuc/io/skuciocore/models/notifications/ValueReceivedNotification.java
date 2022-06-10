package skuc.io.skuciocore.models.notifications;

public class ValueReceivedNotification extends Notification {
  private String deviceId;
  private String deviceType;
  private String paramName;
  private float value;

  public ValueReceivedNotification() {
  }

  public ValueReceivedNotification(String deviceId, String deviceType, String paramName, float value, String namespaceId) {
    super("ValueReceived", namespaceId);
    this.deviceId = deviceId;
    this.deviceType = deviceType;
    this.paramName = paramName;
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
