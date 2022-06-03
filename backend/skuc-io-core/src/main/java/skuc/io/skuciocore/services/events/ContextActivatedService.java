package skuc.io.skuciocore.services.events;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.events.context.ContextActivated;
import skuc.io.skuciocore.persistence.events.ContextActivatedRepository;

@Service
public class ContextActivatedService extends CrudEventsService<ContextActivated> {
  
  private ContextActivatedRepository _contextActivatedRepository;

  @Autowired
  public ContextActivatedService(ContextActivatedRepository contextActivatedRepository) {
    super(contextActivatedRepository);
    this._contextActivatedRepository = contextActivatedRepository;
  }

  public ContextActivated getLatestFor(String contextId, String locationId) {
    return this._contextActivatedRepository.getLatestFor(contextId, locationId);
  }

  public ContextActivated getLatestFor(String contextId, String locationId, LocalDateTime stateTime) {
    return this._contextActivatedRepository.getLatestFor(contextId, locationId, stateTime);
  }
}
