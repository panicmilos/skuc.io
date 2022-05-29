package skuc.io.skuciocore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.Group;
import skuc.io.skuciocore.persistence.GroupRepository;

@Service
public class GroupService extends CrudService<Group> {

  private GroupRepository _groupRepository;

  @Autowired
  public GroupService(GroupRepository repository) {
    super(repository);
    _groupRepository = repository;
  }

  @Override
  public Group create(Group group) {
    var existingGroup = _groupRepository.getByName(group.getName());
    if (existingGroup != null) {
      throw new BadLogicException(String.format("Group with given name %s already exists.", group.getName()));
    }

    return super.create(group);
  }

  @Override
  public Group update(Group group) {
    var existingGroup = getOrThrow(group.getId());

    var groupWithSameName = _groupRepository.getByName(group.getName());
    if (groupWithSameName != null && !groupWithSameName.getId().equals(existingGroup.getId())) {
      throw new BadLogicException(String.format("Group with given name %s already exists.", group.getName()));
    }

    existingGroup.setName(group.getName());

    return super.update(existingGroup);
  }


}
