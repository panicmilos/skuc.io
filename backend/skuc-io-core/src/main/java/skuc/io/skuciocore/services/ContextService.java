package skuc.io.skuciocore.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.configuration.Context;
import skuc.io.skuciocore.models.events.context.ContextActivated;
import skuc.io.skuciocore.models.events.context.ContextDeactivated;
import skuc.io.skuciocore.persistence.ContextRepository;
import skuc.io.skuciocore.services.events.ContextActivatedService;
import skuc.io.skuciocore.services.events.ContextDeactivatedService;

@Service
public class ContextService extends CrudService<Context> {

  private GroupService _groupService;
  private ContextRepository _contextRepository;
  private ContextActivatedService _contextActivatedService;
  private ContextDeactivatedService _contextDeactivatedService;

  @Autowired
  public ContextService(GroupService groupService, ContextRepository repository,
      ContextActivatedService contextActivatedService, ContextDeactivatedService contextDeactivatedService) {
    super(repository);
    _groupService = groupService;
    _contextRepository = repository;
    this._contextActivatedService = contextActivatedService;
    this._contextDeactivatedService = contextDeactivatedService;
  }

  public Collection<Context> getActiveContextsFor(String groupId, String locationId) {
    var result = new ArrayList<Context>();
    for (Context context : _contextRepository.getByGroup(groupId)) {
      if (isContextActiveOn(context.getId(), locationId))
        result.add(context);
    }
    return result;
  }

  public boolean isContextActiveOn(String contextId, String locationId) {
    ContextActivated latestContextActivated = _contextActivatedService.getLatestFor(contextId, locationId);
    ContextDeactivated latestContextDeactivated = _contextDeactivatedService.getLatestFor(contextId, locationId);
    if (latestContextActivated == null) {
      return false;
    } else if (latestContextDeactivated == null) {
      return latestContextActivated != null;
    }
    return latestContextActivated.getFrom().isAfter(latestContextDeactivated.getFrom());
  }

  public Collection<Context> getByGroup(String groupId) {
    return _contextRepository.getByGroup(groupId);
  }

  public Context getByGroupAndName(String groupId, String name) {
    return _contextRepository.getByGroupAndName(groupId, name);
  }

  @Override
  public Context create(Context context) {
    _groupService.getOrThrow(context.getGroupId());

    var contextWithSameName = _contextRepository.getByGroupAndName(context.getGroupId(), context.getName());
    if (contextWithSameName != null) {
      throw new BadLogicException("Context with the same name already exists in the group.");
    }

    return super.create(context);
  }

  @Override
  public Context update(Context context) {
    var existingContext = getOrThrow(context.getId());

    var contextWithSameName = _contextRepository.getByGroupAndName(context.getGroupId(), context.getName());
    if (contextWithSameName != null && !contextWithSameName.getId().equals(existingContext.getId())) {
      throw new BadLogicException("Context with the same name already exists in the group.");
    }

    return super.update(context);
  }

}
