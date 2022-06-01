package skuc.io.skuciocore.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.context.activation.EventActivator;
import skuc.io.skuciocore.persistence.EventActivatorRepository;

@Service
public class EventActivatorService extends CrudService<EventActivator> {

  private ContextService _contextService;
  private EventActivatorRepository _eventActivatorRepository;

  @Autowired
  public EventActivatorService(ContextService contextService, EventActivatorRepository repository) {
    super(repository);
    _contextService = contextService;
    _eventActivatorRepository = repository;
  }

  public Collection<EventActivator> getByGroup(String groupId) {
    return _eventActivatorRepository.getByGroup(groupId);
  }

  public Collection<EventActivator> getByContext(String contextId) {
    return _eventActivatorRepository.getByContext(contextId);
  }

  @Override
  public EventActivator create(EventActivator eventActivator) {
    _contextService.getOrThrow(eventActivator.getContextId());

    var eventActivatorWithSameTypeForContext = _eventActivatorRepository
        .getByContextAndEventType(eventActivator.getContextId(), eventActivator.getEventType());
    if (eventActivatorWithSameTypeForContext != null) {
      throw new BadLogicException("Activator for the given event type already exists for in the context.");
    }

    return super.create(eventActivator);
  }

  @Override
  public EventActivator update(EventActivator eventActivator) {
    var existingEventActivator = getOrThrow(eventActivator.getId());

    var eventActivatorWithSameTypeForContext = _eventActivatorRepository
        .getByContextAndEventType(eventActivator.getContextId(), eventActivator.getEventType());
    if (eventActivatorWithSameTypeForContext != null && !eventActivatorWithSameTypeForContext.getId().equals(existingEventActivator.getId())) {
      throw new BadLogicException("Activator for the given event type already exists for in the context.");
    }

    return super.update(eventActivator);
  }

}
