package skuc.io.skuciocore.persistence.events;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.events.context.ContextDeactivated;

@Repository
public class ContextDeactivatedRepository extends CrudEventsRepository<ContextDeactivated> {

  public ContextDeactivatedRepository() {
    super(ContextDeactivated.class);
  }
  
}

