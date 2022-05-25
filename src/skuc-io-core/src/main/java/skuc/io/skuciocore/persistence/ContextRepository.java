package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.configuration.Context;

@Repository
public class ContextRepository extends CrudRepository<Context> {
  
  public ContextRepository() {
    super(Context.class);
  }

  public Collection<Context> getByGroup(String groupId) {
    return getSession().query(this.concreteClass).whereEquals("groupId", groupId).toList();
  }

  public Context getByGroupAndName(String groupId, String name) {
    return getSession().query(this.concreteClass)
    .whereEquals("groupId", groupId)
    .whereEquals("name", name)
    .firstOrDefault();
  }

}
