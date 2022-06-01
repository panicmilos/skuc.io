package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.context.deactivation.EventDeactivator;

@Repository
public class EventDeactivatorRepository extends CrudRepository<EventDeactivator> {
  
  public EventDeactivatorRepository() {
    super(EventDeactivator.class);
  }

  public Collection<EventDeactivator> getByContext(String contextId) {
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("contextId", contextId).toList();
    }
  }

  public EventDeactivator getByContextAndEventType(String contextId, String eventType) {
    try (var session = getSession()) {
      return session.query(this.concreteClass)
        .whereEquals("contextId", contextId)
        .whereEquals("eventType", eventType)
        .firstOrDefault();
    }
  }

  @Override
  public void update(EventDeactivator eventDeactivator) {
    try (var session = getSession()) {
      var existingEventDeactivator = session.load(concreteClass, eventDeactivator.getId());

      existingEventDeactivator.setEventType(eventDeactivator.getEventType());

      session.saveChanges();
    }
    
  }

}
