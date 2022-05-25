package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.User;

@Repository
public class UserRepository extends CrudRepository<User> {
  
  public UserRepository() {
    super(User.class);
  }

  public Collection<User> getByGroup(String groupId) {
    return getSession().query(this.concreteClass).whereEquals("groupId", groupId).toList();
  }

  public User getByEmail(String email) {
    return getSession().query(this.concreteClass).whereEquals("email", email).firstOrDefault();
  }

}
