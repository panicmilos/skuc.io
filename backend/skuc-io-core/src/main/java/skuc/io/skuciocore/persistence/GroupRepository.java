package skuc.io.skuciocore.persistence;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.Group;

@Repository
public class GroupRepository extends CrudRepository<Group> {

  public GroupRepository() {
    super(Group.class);
  }

  public Group getByName(String name) {
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("name", name).firstOrDefault();
    }
  }

  @Override
  public void update(Group group) {
    try (var session = getSession()) {
      var existingGroup = session.load(concreteClass, group.getId());
      
      existingGroup.setName(group.getName());

      session.saveChanges();
    }
    
  }
}
