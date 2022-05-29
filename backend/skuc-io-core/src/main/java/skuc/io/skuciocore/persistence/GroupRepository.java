package skuc.io.skuciocore.persistence;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.Group;

@Repository
public class GroupRepository extends CrudRepository<Group> {

  public GroupRepository() {
    super(Group.class);
  }

  public Group getByName(String name) {
    return getSession().query(this.concreteClass).whereEquals("name", name).firstOrDefault();
  }
}
