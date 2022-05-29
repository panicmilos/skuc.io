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
    return getSession().query(this.concreteClass).whereEquals("groupId", groupId).toList();
  }

  public Location getByGroupAndName(String groupId, String name) {
    return getSession().query(this.concreteClass)
    .whereEquals("groupId", groupId)
    .whereEquals("name", name)
    .firstOrDefault();
  }


}
