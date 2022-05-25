package skuc.io.skucioapp.api_contracts.requests.Devices;

public class UpdateDeviceRequest {
  private String name;

  public UpdateDeviceRequest() {

  }

  public UpdateDeviceRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
