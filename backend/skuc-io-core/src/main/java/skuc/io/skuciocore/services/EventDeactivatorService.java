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

  public Collection<EventDeactivator> getByGroup(String groupId) {
    return _eventDeactivatorRepository.getByGroup(groupId);
  }

  public Collection<EventDeactivator> getByContext(String contextId) {
    return _eventDeactivatorRepository.getByContext(contextId);
  }

  @Override
  public EventDeactivator create(EventDeactivator eventDeactivator) {
    _contextService.getOrThrow(eventDeactivator.getContextId());

    var eventDeactivatorWithSameTypeForContext = _eventDeactivatorRepository
        .getByContextAndEventType(eventDeactivator.getContextId(), eventDeactivator.getEventType());
    if (eventDeactivatorWithSameTypeForContext != null) {
      throw new BadLogicException("Deactivator for the given event type already exists for in the context.");
    }

    return super.create(eventDeactivator);
  }

  @Override
  public EventDeactivator update(EventDeactivator eventDeactivator) {
    var existingEventDeactivator = getOrThrow(eventDeactivator.getId());

    var eventDeactivatorWithSameTypeForContext = _eventDeactivatorRepository
        .getByContextAndEventType(eventDeactivator.getContextId(), eventDeactivator.getEventType());
    if (eventDeactivatorWithSameTypeForContext != null && !eventDeactivatorWithSameTypeForContext.getId().equals(existingEventDeactivator.getId())) {
      throw new BadLogicException("Deactivator for the given event type already exists for in the context.");
    }

    return super.update(eventDeactivator);
  }

}
