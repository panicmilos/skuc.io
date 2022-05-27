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

  public Collection<EventActivator> getByContext(String contextId) {
    return _eventActivatorRepository.getByContext(contextId);
  }

  @Override
  public EventActivator create(EventActivator eventActivator) {
    _contextService.getOrThrow(eventActivator.getContextId().toString());

    var eventActivatorWithSameTypeForContext = _eventActivatorRepository
        .getByContextAndEventType(eventActivator.getContextId().toString(), eventActivator.getEventType());
    if (eventActivatorWithSameTypeForContext.size() != 0) {
      throw new BadLogicException("EventActivator with the same type already exists for the context.");
    }

    return super.create(eventActivator);
  }

  @Override
  public EventActivator update(EventActivator eventActivator) {
    var existingEventActivator = getOrThrow(eventActivator.getId());

    var eventActivatorWithSameTypeForContext = _eventActivatorRepository
        .getByContextAndEventType(eventActivator.getContextId().toString(), eventActivator.getEventType());
    if (eventActivatorWithSameTypeForContext.size() != 0) {
      throw new BadLogicException("EventActivator with the same type already exists for the context.");
    }

    existingEventActivator.setEventType(eventActivator.getEventType());

    return super.update(existingEventActivator);
  }

}
