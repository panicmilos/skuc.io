package skuc.io.skucioapp.api_contracts.requests.Devices;

public class CreateDeviceRequest {
  private String name;
  private String deviceId;

  public CreateDeviceRequest() {

  }

  public CreateDeviceRequest(String name, String deviceId) {
    this.name = name;
    this.deviceId = deviceId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

}
