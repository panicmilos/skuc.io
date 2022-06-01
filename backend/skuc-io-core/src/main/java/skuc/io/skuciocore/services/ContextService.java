package skuc.io.skuciocore.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.configuration.Context;
import skuc.io.skuciocore.persistence.ContextRepository;

@Service
public class ContextService extends CrudService<Context> {

  private GroupService _groupService;
  private ContextRepository _contextRepository;

  @Autowired
  public ContextService(GroupService groupService, ContextRepository repository) {
    super(repository);
    _groupService = groupService;
    _contextRepository = repository;
  }

  public Collection<Context> getByGroup(String groupId) {
    return _contextRepository.getByGroup(groupId);
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
