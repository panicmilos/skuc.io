package skuc.io.skuciocore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.csm.User;
import skuc.io.skuciocore.persistence.UserRepository;

@Service
public class UserService extends CrudService<User> {
  
  @Autowired
  public UserService(UserRepository repository) {
    super(repository);
  }
}
