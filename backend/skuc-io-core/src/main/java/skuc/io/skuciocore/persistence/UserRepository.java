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
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("groupId", groupId).toList();
    }
  }

  public User getByEmail(String email) {
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("email", email).firstOrDefault();
    }
  }

  @Override
  public void update(User user) {
    try (var session = getSession()) {
      var existingUser = session.load(concreteClass, user.getId());

      existingUser.setFullName(user.getFullName());
      existingUser.setAddress(user.getAddress());
      existingUser.setPhoneNumber(existingUser.getPhoneNumber());  

      session.saveChanges();
    }
  }

  public void changePassword(User user) {
    try (var session = getSession()) {
      var existingUser = session.load(concreteClass, user.getId());

      existingUser.setPassword(user.getPassword());

      session.saveChanges();
    }
    
  }

}
