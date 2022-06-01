package skuc.io.skuciocore.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.Location;
import skuc.io.skuciocore.persistence.LocationRepository;

@Service
public class LocationService extends CrudService<Location> {
  
  private GroupService _groupService;
  private LocationRepository _locationRepository;

  @Autowired
  public LocationService(GroupService groupService, LocationRepository repository) {
    super(repository);
    _groupService = groupService;
    _locationRepository = repository;
  }

  public Collection<Location> getByGroup(String groupId) {
    return _locationRepository.getByGroup(groupId);
  }

  @Override
  public Location create(Location location) {
    _groupService.getOrThrow(location.getGroupId());

    var locationWithSameName = _locationRepository.getByGroupAndName(location.getGroupId(), location.getName());
    if (locationWithSameName != null) {
      throw new BadLogicException("Location with the same name already exists in the group.");
    }

    return super.create(location);
  }

  @Override
  public Location update(Location location) {
    var existingLocation = getOrThrow(location.getId());

    var locationWithSameName = _locationRepository.getByGroupAndName(location.getGroupId(), location.getName());
    if (locationWithSameName != null && !locationWithSameName.getId().equals(existingLocation.getId())) {
      throw new BadLogicException("Location with the same name already exists in the group.");
    }

    return super.update(location);
  }

}
