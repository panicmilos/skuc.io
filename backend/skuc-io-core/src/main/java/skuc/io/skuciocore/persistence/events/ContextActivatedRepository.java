package skuc.io.skuciocore.persistence.events;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.events.context.ContextActivated;

@Repository
public class ContextActivatedRepository extends CrudEventsRepository<ContextActivated> {

  public ContextActivatedRepository() {
    super(ContextActivated.class);
  }
  
}
