package skuc.io.skuciocore.models.events.kjar;

public class AggregateParam {

  private String deviceId;
  private String deviceType;
  private String paramName;
  private int resolution;

  public AggregateParam() {
  }

  public AggregateParam(String deviceId, String deviceType, String paramName, int resolution) {
    this.deviceId = deviceId;
    this.deviceType = deviceType;
    this.paramName = paramName;
    this.resolution = resolution;
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

  public int getResolution() {
    return this.resolution;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

}
