package skuc.io.skucioapp.api_contracts.requests.Contexts;

public class DeactivateContextRequest {
  private String locationId;

  public DeactivateContextRequest() {
  }

  public DeactivateContextRequest(String locationId) {
    this.locationId = locationId;
  }

  public String getLocationId() {
    return this.locationId;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

}
