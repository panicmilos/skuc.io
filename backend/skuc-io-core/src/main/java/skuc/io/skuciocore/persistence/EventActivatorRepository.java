package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.context.activation.EventActivator;

@Repository
public class EventActivatorRepository extends CrudRepository<EventActivator> {
  
  public EventActivatorRepository() {
    super(EventActivator.class);
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
