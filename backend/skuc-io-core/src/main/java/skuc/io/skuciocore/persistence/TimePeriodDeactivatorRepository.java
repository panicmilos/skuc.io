package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.ravendb.client.documents.operations.DeleteByQueryOperation;
import net.ravendb.client.documents.queries.IndexQuery;
import skuc.io.skuciocore.models.csm.context.deactivation.TimePeriodDeactivator;

@Repository
public class TimePeriodDeactivatorRepository extends CrudRepository<TimePeriodDeactivator> {

  private final ContextRepository _contextRepository;

  @Autowired
  public TimePeriodDeactivatorRepository(ContextRepository contextRepository) {
    super(TimePeriodDeactivator.class);
    _contextRepository = contextRepository;
  }

  public Collection<TimePeriodDeactivator> getByGroup(String groupId) {
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

  public void deleteByContext(String contextId) {
    DocumentStoreHolder.getStore()
      .operations()
      .send(new DeleteByQueryOperation(new IndexQuery(String.format("from TimePeriodDeactivators where contextId = '%s'", contextId))));
  }

}
