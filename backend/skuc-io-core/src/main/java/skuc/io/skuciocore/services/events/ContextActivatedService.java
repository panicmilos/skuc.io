package skuc.io.skuciocore.services.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.events.context.ContextActivated;
import skuc.io.skuciocore.persistence.events.ContextActivatedRepository;

@Service
public class ContextActivatedService extends CrudEventsService<ContextActivated> {
  
  @Autowired
  public ContextActivatedService(ContextActivatedRepository contextActivatedRepository) {
    super(contextActivatedRepository);
  }
}
