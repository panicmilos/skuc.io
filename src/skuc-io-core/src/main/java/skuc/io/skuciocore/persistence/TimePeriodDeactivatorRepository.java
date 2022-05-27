package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.context.deactivation.TimePeriodDeactivator;

@Repository
public class TimePeriodDeactivatorRepository extends CrudRepository<TimePeriodDeactivator> {
  
  public TimePeriodDeactivatorRepository() {
    super(TimePeriodDeactivator.class);
  }

  public Collection<TimePeriodDeactivator> getByContext(String contextId) {
    return getSession().query(this.concreteClass).whereEquals("contextId", contextId).toList();
  }

}
