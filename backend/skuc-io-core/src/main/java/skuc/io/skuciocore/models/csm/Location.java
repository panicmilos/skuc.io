package skuc.io.skuciocore.models.csm;

public class Location extends BaseCsm {
  private String name;
  private double lng;
  private double lat;
  private String groupId;

  public Location() {
  }

  public Location(String name, double lng, double lat, String groupId) {
    this.name = name;
    this.lng = lng;
    this.lat = lat;
    this.groupId = groupId;
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

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

}