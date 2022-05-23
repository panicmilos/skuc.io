package skuc.io.skuciocore.models.csm;

import java.util.UUID;

public class Device extends BaseCsm {
  private String name;
  private UUID locationId;
  private String deviceId;

  public Device() {
  }

  public Device(String Name, UUID LocationId, String DeviceId) {
    this.name = Name;
    this.locationId = LocationId;
    this.deviceId = DeviceId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getLocationId() {
    return this.locationId;
  }

  public void setLocationId(UUID locationId) {
    this.locationId = locationId;
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

}