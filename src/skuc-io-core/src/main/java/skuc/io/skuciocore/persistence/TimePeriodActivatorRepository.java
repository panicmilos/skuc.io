package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.context.activation.TimePeriodActivator;

@Repository
public class TimePeriodActivatorRepository extends CrudRepository<TimePeriodActivator> {
  
  public TimePeriodActivatorRepository() {
    super(TimePeriodActivator.class);
  }

  public Collection<TimePeriodActivator> getByContext(String contextId) {
    return getSession().query(this.concreteClass).whereEquals("contextId", contextId).toList();
  }

}
