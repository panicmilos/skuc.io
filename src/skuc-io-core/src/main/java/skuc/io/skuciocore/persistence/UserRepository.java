package skuc.io.skuciocore.persistence;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.User;

@Repository
public class UserRepository extends CrudRepository<User> {
  
  public UserRepository() {
    super(User.class);
  }
}
