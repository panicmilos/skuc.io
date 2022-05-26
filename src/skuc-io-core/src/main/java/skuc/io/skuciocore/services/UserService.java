package skuc.io.skuciocore.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.exceptions.MissingEntityException;
import skuc.io.skuciocore.models.csm.User;
import skuc.io.skuciocore.persistence.UserRepository;
import skuc.io.skuciocore.utils.ArrayUtils;

@Service
public class UserService extends CrudService<User> implements UserDetailsService {

  private GroupService _groupService;
  private UserRepository _userRepository;

  @Autowired
  public UserService(GroupService groupService, UserRepository repository) {
    super(repository);
    _groupService = groupService;
    _userRepository = repository;
  }

  public Collection<User> getByGroup(String groupId) {
    return _userRepository.getByGroup(groupId);
  }

  @Override
  public User create(User user) {
    _groupService.getOrThrow(user.getGroupId());

    var userWithSameEmail = _userRepository.getByEmail(user.getEmail());
    if (userWithSameEmail != null) {
      throw new BadLogicException("User with the same email already exists in the system.");
    }

    return super.create(user);
  }

  public User getByEmail(String email) {
    var user = _userRepository.getByEmail(email);
    if (user == null) {
      throw new MissingEntityException("User with email " + email + " does not exist.");
    }

    return user;
  }

  @Override
  public User update(User user) {
    var existingUser = getOrThrow(user.getId());

    existingUser.setFullName(user.getFullName());
    existingUser.setAddress(user.getAddress());
    existingUser.setPhoneNumber(existingUser.getPhoneNumber());

    return super.update(user);
  }

  public User changePassword(String userId, String currentPassword, String newPassword) {
    var existingUser = getOrThrow(userId);

    if (!existingUser.getPassword().equals(currentPassword)) {
      throw new BadLogicException("Current passwords do not match.");
    }

    existingUser.setPassword(newPassword);

    return super.update(existingUser);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = _userRepository.getByEmail(username);
    if (user == null) {
      throw new MissingEntityException("User with email " + username + " does not exist!");
    }
    var userAuthorities = getUserAuthorities(user);

    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), userAuthorities);
  }

  private List<GrantedAuthority> getUserAuthorities(User user) {
    var grantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());

    return ArrayUtils.spread(grantedAuthority);
  }

}
