package skuc.io.skuciocore.models.csm;

public class Device extends BaseCsm {
  private String name;
  private String groupId;
  private String locationId;
  private String deviceId;

  public Device() {
  }

  public Device(String Name, String groupId, String LocationId, String DeviceId) {
    this.name = Name;
    this.groupId = groupId;
    this.locationId = LocationId;
    this.deviceId = DeviceId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getLocationId() {
    return this.locationId;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

}