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
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("contextId", contextId).toList();
    }
  }

  @Override
  public void update(TimePeriodDeactivator timePeriodDeactivator) {
    try (var session = getSession()) {
      var existingTimePeriodDeactivator = session.load(concreteClass, timePeriodDeactivator.getId());
      
      existingTimePeriodDeactivator.setCronStart(timePeriodDeactivator.getCronStart());
      existingTimePeriodDeactivator.setCronEnd(timePeriodDeactivator.getCronEnd());  

      session.saveChanges();
    }
    
  }

}
