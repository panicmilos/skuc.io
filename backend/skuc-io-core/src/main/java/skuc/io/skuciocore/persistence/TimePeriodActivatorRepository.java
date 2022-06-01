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
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("contextId", contextId).toList();
    }
  }

  @Override
  public void update(TimePeriodActivator timePeriodActivator) {
    try (var session = getSession()) {
      var existingTimePeriodActivator = session.load(concreteClass, timePeriodActivator.getId());

      existingTimePeriodActivator.setCronStart(timePeriodActivator.getCronStart());
      existingTimePeriodActivator.setCronEnd(timePeriodActivator.getCronEnd());
  
      session.saveChanges();
    }
    
  }

}
