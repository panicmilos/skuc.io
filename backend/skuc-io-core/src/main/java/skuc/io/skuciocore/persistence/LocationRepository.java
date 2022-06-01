package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.Location;

@Repository
public class LocationRepository extends CrudRepository<Location> {
  
  public LocationRepository() {
    super(Location.class);
  }

  public Collection<Location> getByGroup(String groupId) {
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("groupId", groupId).toList();
    }
  }

  public Location getByGroupAndName(String groupId, String name) {
    try (var session = getSession()) {
      return session.query(this.concreteClass)
        .whereEquals("groupId", groupId)
        .whereEquals("name", name)
        .firstOrDefault();
    }
  }

  @Override
  public void update(Location location) {
    try (var session = getSession()) {
      var existingLocation = session.load(concreteClass, location.getId());

      existingLocation.setName(location.getName());
      existingLocation.setLng(location.getLng());
      existingLocation.setLat(location.getLat());

      session.saveChanges();
    }
    
  }

}
