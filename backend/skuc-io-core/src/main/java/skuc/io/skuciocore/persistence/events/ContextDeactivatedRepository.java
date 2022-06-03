package skuc.io.skuciocore.persistence.events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.events.context.ContextDeactivated;

@Repository
public class ContextDeactivatedRepository extends CrudEventsRepository<ContextDeactivated> {

  public ContextDeactivatedRepository() {
    super(ContextDeactivated.class);
  }

  public ContextDeactivated getLatestFor(String contextId, String locationId) {
    return getLatestFor(contextId, locationId, LocalDateTime.now());
  }

  public ContextDeactivated getLatestFor(String contextId, String locationId, LocalDateTime stateTime) {
    return this.getSession()
      .query(this.concreteClass)
      .whereEquals("contextId", contextId)
      .whereEquals("locationId", locationId)
      .whereLessThan("from", stateTime.format(DateTimeFormatter.ISO_DATE_TIME))
      .orderByDescending("from")
      .firstOrDefault();
  }
  
}

