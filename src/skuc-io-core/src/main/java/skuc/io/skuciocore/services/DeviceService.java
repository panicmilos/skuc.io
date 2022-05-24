package skuc.io.skuciocore.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.Device;
import skuc.io.skuciocore.persistence.DeviceRepository;

@Service
public class DeviceService extends CrudService<Device> {

  private LocationService _locationService;
  private DeviceRepository _deviceRepository;

  @Autowired
  public DeviceService(LocationService locationService, DeviceRepository repository) {
    super(repository);
    _locationService = locationService;
    _deviceRepository = repository;
  }

  public Collection<Device> getByLocation(String locationId) {
    return _deviceRepository.getByLocation(locationId);
  }

  @Override
  public Device create(Device device) {
    _locationService.getOrThrow(device.getLocationId());

    var deviceWithSameId = _deviceRepository.getByDevice(device.getDeviceId());
    if (deviceWithSameId != null) {
      throw new BadLogicException("Device with the same id already exists in the system.");
    }

    var deviceWithSameName = _deviceRepository.getByLocationAndName(device.getLocationId(), device.getName());
    if (deviceWithSameName != null) {
      throw new BadLogicException("Device with the same name already exists at the location.");
    }

    return super.create(device);
  }

  @Override
  public Device update(Device device) {
    var existingDevice = getOrThrow(device.getId());

    var deviceWithSameName = _deviceRepository.getByLocationAndName(device.getLocationId(), device.getName());
    if (deviceWithSameName != null && !deviceWithSameName.getId().equals(existingDevice.getId())) {
      throw new BadLogicException("Device with the same name already exists at the location.");
    }

    existingDevice.setName(device.getName());

    return super.update(device);
  }
}
