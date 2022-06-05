package skuc.io.skucioapp.api_contracts.requests.Events;

public class StatusReceivedRequest {
  private String value;
  private String deviceType;
  private String streamId;

  public StatusReceivedRequest() {
  }

  public StatusReceivedRequest(String value, String deviceType, String streamId) {
    this.value = value;
    this.deviceType = deviceType;
    this.streamId = streamId;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
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
