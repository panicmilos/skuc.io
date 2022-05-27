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
    return getSession().query(this.concreteClass).whereEquals("contextId", contextId).toList();
  }

  public EventDeactivator getByContextAndEventType(String contextId, String eventType) {
    return getSession().query(this.concreteClass)
      .whereEquals("contextId", contextId)
      .whereEquals("eventType", eventType)
      .firstOrDefault();
  }

}
