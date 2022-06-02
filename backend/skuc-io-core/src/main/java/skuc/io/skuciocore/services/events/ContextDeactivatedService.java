package skuc.io.skuciocore.services.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.events.context.ContextDeactivated;
import skuc.io.skuciocore.persistence.events.ContextDeactivatedRepository;

@Service
public class ContextDeactivatedService extends CrudEventsService<ContextDeactivated> {
  
  @Autowired
  public ContextDeactivatedService(ContextDeactivatedRepository contextDeactivatedRepository) {
    super(contextDeactivatedRepository);
  }
}
