package skuc.io.skucioapp.api_contracts.requests.Locations;

public class CreateLocationRequest {
  private String name;
  private double lng;
  private double lat;

  public CreateLocationRequest() {
  }

  public CreateLocationRequest(String name, double lng, double lat) {
    this.name = name;
    this.lng = lng;
    this.lat = lat;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLng() {
    return this.lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }

  public double getLat() {
    return this.lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

}
