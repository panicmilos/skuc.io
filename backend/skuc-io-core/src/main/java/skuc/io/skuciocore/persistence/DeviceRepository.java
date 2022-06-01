package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.Device;

@Repository
public class DeviceRepository extends CrudRepository<Device> {
  
  public DeviceRepository() {
    super(Device.class);
  }

  public Collection<Device> getByLocation(String locationId) {
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("locationId", locationId).toList();
    }
  }

  public Device getByDevice(String deviceId) {
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("deviceId", deviceId).firstOrDefault();
    }
  }

  public Device getByLocationAndName(String locationId, String name) {
    try (var session = getSession()) {
      return session.query(this.concreteClass)
        .whereEquals("locationId", locationId)
        .whereEquals("name", name)
        .firstOrDefault();
    }
  }

  @Override
  public void update(Device device) {
    try (var session = getSession()) {
      var existingDevice = session.load(concreteClass, device.getDeviceId());

      existingDevice.setName(device.getName());

      session.saveChanges();
    }
  }


}
