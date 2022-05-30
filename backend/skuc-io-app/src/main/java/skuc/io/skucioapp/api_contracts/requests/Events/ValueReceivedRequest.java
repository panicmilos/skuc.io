package skuc.io.skucioapp.api_contracts.requests.Events;

public class ValueReceivedRequest {
  private String paramName;
  private float value;
  private String deviceType;
  private String streamId;

  public ValueReceivedRequest() {
  }

  public ValueReceivedRequest(String paramName, float value, String deviceType, String streamId) {
    this.paramName = paramName;
    this.value = value;
    this.deviceType = deviceType;
    this.streamId = streamId;
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

  public String getDeviceType() {
    return this.deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getStreamId() {
    return this.streamId;
  }

  public void setStreamId(String streamId) {
    this.streamId = streamId;
  }

}
