package skuc.io.skucioapp.controllers;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.api_contracts.requests.Devices.CreateDeviceRequest;
import skuc.io.skucioapp.api_contracts.requests.Devices.UpdateDeviceRequest;
import skuc.io.skuciocore.models.csm.Device;
import skuc.io.skuciocore.services.DeviceService;

@RestController
@RequestMapping("groups")
public class DevicesController {

  private final DeviceService _deviceService;
  private final ModelMapper _mapper;

  @Autowired
  public DevicesController(DeviceService deviceService, ModelMapper mapper) {
    _deviceService = deviceService;
    _mapper = mapper;
  }

  @GetMapping("{groupId}/locations/{locationId}/devices")
  public ResponseEntity<Collection<Device>> getDevices(@PathVariable String locationId) {
    return ResponseEntity.ok(_deviceService.getByLocation(locationId));
  }

  @GetMapping("{groupId}/locations/{locationId}/devices/{deviceId}")
  public ResponseEntity<Device> getDevice(@PathVariable String deviceId) {
    return ResponseEntity.ok(_deviceService.getOrThrow(deviceId));
  }

  @PostMapping("{groupId}/locations/{locationId}/devices")
  public ResponseEntity<Device> createDevice(@PathVariable String groupId, @PathVariable String locationId, @RequestBody CreateDeviceRequest request) {
    var device = _mapper.map(request, Device.class);
    device.setGroupId(groupId);
    device.setLocationId(locationId);

    return ResponseEntity.ok(_deviceService.create(device));
  }

  @PutMapping("{groupId}/locations/{locationId}/devices/{deviceId}")
  public ResponseEntity<Device> updateDevice(@PathVariable String groupId, @PathVariable String locationId, @PathVariable String deviceId, @RequestBody UpdateDeviceRequest request) {
    var device = _mapper.map(request, Device.class);
    device.setId(deviceId);
    device.setGroupId(groupId);
    device.setLocationId(locationId);

    return ResponseEntity.ok(_deviceService.update(device));
  }

  @DeleteMapping("{groupId}/locations/{locationId}/devices/{deviceId}")
  public ResponseEntity<Device> deleteDevice(@PathVariable String deviceId) {
    return ResponseEntity.ok(_deviceService.delete(deviceId));
  }
  
}
