package skuc.io.skuciocore.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.context.deactivation.EventDeactivator;
import skuc.io.skuciocore.persistence.EventDeactivatorRepository;

@Service
public class EventDeactivatorService extends CrudService<EventDeactivator> {

  private ContextService _contextService;
  private EventDeactivatorRepository _eventDeactivatorRepository;

  @Autowired
  public EventDeactivatorService(ContextService contextService, EventDeactivatorRepository repository) {
    super(repository);
    _contextService = contextService;
    _eventDeactivatorRepository = repository;
  }

  public Collection<EventDeactivator> getByContext(String contextId) {
    return _eventDeactivatorRepository.getByContext(contextId);
  }

  @Override
  public EventDeactivator create(EventDeactivator eventDeactivator) {
    _contextService.getOrThrow(eventDeactivator.getContextId().toString());

    var eventDeactivatorWithSameTypeForContext = _eventDeactivatorRepository
        .getByContextAndEventType(eventDeactivator.getContextId().toString(), eventDeactivator.getEventType());
    if (eventDeactivatorWithSameTypeForContext.size() != 0) {
      throw new BadLogicException("EventDeactivator with the same type already exists for the context.");
    }

    return super.create(eventDeactivator);
  }

  @Override
  public EventDeactivator update(EventDeactivator eventDeactivator) {
    var existingEventDeactivator = getOrThrow(eventDeactivator.getId());

    var eventDeactivatorWithSameTypeForContext = _eventDeactivatorRepository
        .getByContextAndEventType(eventDeactivator.getContextId().toString(), eventDeactivator.getEventType());
    if (eventDeactivatorWithSameTypeForContext.size() != 0) {
      throw new BadLogicException("EventDeactivator with the same type already exists for the context.");
    }

    existingEventDeactivator.setEventType(eventDeactivator.getEventType());

    return super.update(existingEventDeactivator);
  }

}
