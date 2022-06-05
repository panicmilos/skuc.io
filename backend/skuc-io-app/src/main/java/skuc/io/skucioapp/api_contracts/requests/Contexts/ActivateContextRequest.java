package skuc.io.skucioapp.api_contracts.requests.Contexts;

public class ActivateContextRequest {
  private String locationId;

  public ActivateContextRequest() {
  }

  public ActivateContextRequest(String locationId) {
    this.locationId = locationId;
  }

  public String getLocationId() {
    return this.locationId;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

}
