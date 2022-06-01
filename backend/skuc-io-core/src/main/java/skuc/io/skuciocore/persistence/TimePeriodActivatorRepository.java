package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.context.activation.TimePeriodActivator;

@Repository
public class TimePeriodActivatorRepository extends CrudRepository<TimePeriodActivator> {
  
  private final ContextRepository _contextRepository;

  @Autowired
  public TimePeriodActivatorRepository(ContextRepository contextRepository) {
    super(TimePeriodActivator.class);
    _contextRepository = contextRepository;
  }

  public Collection<TimePeriodActivator> getByGroup(String groupId) {
    var contextsInGroup = _contextRepository.getByGroup(groupId);

    try (var session = getSession()) {
      var query = session.query(this.concreteClass);
      
      for (var context : contextsInGroup) {
        query = query.whereEquals("contextId", context.getId()).orElse();
      }
      
      query.whereEquals("groupId", groupId);
      
      return query.toList();
    }
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
