package skuc.io.skuciocore.persistence.events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.events.context.ContextActivated;

@Repository
public class ContextActivatedRepository extends CrudEventsRepository<ContextActivated> {

  public ContextActivatedRepository() {
    super(ContextActivated.class);
  }

  public ContextActivated getLatestFor(String contextId, String locationId) {
    return getLatestFor(contextId, locationId, LocalDateTime.now());
  }

  public ContextActivated getLatestFor(String contextId, String locationId, LocalDateTime stateTime) {
    return this.getSession()
      .query(this.concreteClass)
      .whereEquals("contextId", contextId)
      .whereEquals("locationId", locationId)
      .whereLessThan("from", stateTime.format(DateTimeFormatter.ISO_DATE_TIME))
      .orderByDescending("from")
      .firstOrDefault();
  }
  
}
