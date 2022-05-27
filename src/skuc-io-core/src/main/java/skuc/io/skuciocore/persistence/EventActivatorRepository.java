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
    return getSession().query(this.concreteClass).whereEquals("contextId", contextId).toList();
  }

  public Collection<EventActivator> getByContextAndEventType(String contextId, String eventType) {
    return getSession().query(this.concreteClass)
    .whereEquals("contextId", contextId)
    .whereEquals("eventType", eventType)
    .toList();
  }

}
