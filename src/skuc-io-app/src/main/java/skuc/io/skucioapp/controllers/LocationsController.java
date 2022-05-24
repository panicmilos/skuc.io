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

import skuc.io.skucioapp.api_contracts.requests.Locations.CreateLocationRequest;
import skuc.io.skucioapp.api_contracts.requests.Locations.UpdateLocationRequest;
import skuc.io.skuciocore.models.csm.Location;
import skuc.io.skuciocore.services.LocationService;

@RestController
@RequestMapping("groups")
public class LocationsController {
 
  private final LocationService _locationService;
  private final ModelMapper _mapper;

  @Autowired
  public LocationsController(LocationService locationService, ModelMapper mapper) {
    _locationService = locationService;
    _mapper = mapper;
  }

  @GetMapping("{groupId}/locations")
  public ResponseEntity<Collection<Location>> getLocations(@PathVariable String groupId) {
    return ResponseEntity.ok(_locationService.getByGroup(groupId));
  }

  @GetMapping("{groupId}/locations/{locationId}")
  public ResponseEntity<Location> getLocation(@PathVariable String locationId) {
    return ResponseEntity.ok(_locationService.get(locationId));
  }

  @PostMapping("{groupId}/locations")
  public ResponseEntity<Location> createLocation(@PathVariable String groupId, @RequestBody CreateLocationRequest request) {
    var location = _mapper.map(request, Location.class);
    location.setGroupId(groupId);

    return ResponseEntity.ok(_locationService.create(location));
  }

  @PutMapping("{groupId}/locations/{locationId}")
  public ResponseEntity<Location> updateLocation(@PathVariable String groupId, @PathVariable String locationId, @RequestBody UpdateLocationRequest request) {
    var location = _mapper.map(request, Location.class);
    location.setId(locationId);
    location.setGroupId(groupId);

    return ResponseEntity.ok(_locationService.update(location));
  }

  @DeleteMapping("{groupId}/locations/{locationId}")
  public ResponseEntity<Location> deleteLocation(@PathVariable String locationId) {
    return ResponseEntity.ok(_locationService.delete(locationId));
  }
}
