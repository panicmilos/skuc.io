package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.context.activation.EventActivator;

@Repository
public class EventActivatorRepository extends CrudRepository<EventActivator> {
  
  private final ContextRepository _contextRepository;

  @Autowired
  public EventActivatorRepository(ContextRepository contextRepository) {
    super(EventActivator.class);
    _contextRepository = contextRepository;
  }

  public Collection<EventActivator> getByGroup(String groupId) {
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

  public Collection<EventActivator> getByContext(String contextId) {
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("contextId", contextId).toList();
    }
  }

  public EventActivator getByContextAndEventType(String contextId, String eventType) {
    try (var session = getSession()) {
      return session.query(this.concreteClass)
        .whereEquals("contextId", contextId)
        .whereEquals("eventType", eventType)
        .firstOrDefault();
    }
  }

  @Override
  public void update(EventActivator eventActivator) {
    try (var session = getSession()) {
      var existingEventActivator = session.load(concreteClass, eventActivator.getId());

      existingEventActivator.setEventType(eventActivator.getEventType());

      session.saveChanges();
    }

    
  }

}
