package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ravendb.client.documents.operations.DeleteByQueryOperation;
import net.ravendb.client.documents.queries.IndexQuery;
import skuc.io.skuciocore.models.csm.context.deactivation.EventDeactivator;

@Repository
public class EventDeactivatorRepository extends CrudRepository<EventDeactivator> {
  
  private final ContextRepository _contextRepository;

  @Autowired
  public EventDeactivatorRepository(ContextRepository contextRepository) {
    super(EventDeactivator.class);
    _contextRepository = contextRepository;
  }

  public Collection<EventDeactivator> getByGroup(String groupId) {
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

  public void deleteByContext(String contextId) {
    DocumentStoreHolder.getStore()
      .operations()
      .send(new DeleteByQueryOperation(new IndexQuery(String.format("from EventDeactivators where contextId = '%s'", contextId))));
  }

}
