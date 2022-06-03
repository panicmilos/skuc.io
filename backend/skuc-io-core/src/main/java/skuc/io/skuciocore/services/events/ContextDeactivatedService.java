package skuc.io.skuciocore.services.events;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.events.context.ContextDeactivated;
import skuc.io.skuciocore.persistence.events.ContextDeactivatedRepository;

@Service
public class ContextDeactivatedService extends CrudEventsService<ContextDeactivated> {
  
  private ContextDeactivatedRepository _contextDeactivatedRepository;

  @Autowired
  public ContextDeactivatedService(ContextDeactivatedRepository contextDeactivatedRepository) {
    super(contextDeactivatedRepository);
    this._contextDeactivatedRepository = contextDeactivatedRepository;
  }

  public ContextDeactivated getLatestFor(String contextId, String locationId) {
    return this._contextDeactivatedRepository.getLatestFor(contextId, locationId);
  }

  public ContextDeactivated getLatestFor(String contextId, String locationId, LocalDateTime stateTime) {
    return this._contextDeactivatedRepository.getLatestFor(contextId, locationId, stateTime);
  }

}
