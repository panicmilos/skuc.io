package skuc.io.skucioapp.controllers;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.api_contracts.requests.Users.ChangePasswordRequest;
import skuc.io.skucioapp.api_contracts.requests.Users.CreateUserRequest;
import skuc.io.skucioapp.api_contracts.requests.Users.UpdateUserRequest;
import skuc.io.skuciocore.models.csm.User;
import skuc.io.skuciocore.services.UserService;

@RestController
@RequestMapping("groups")
public class UsersController {

  private final UserService _userService;
  private final ModelMapper _mapper;

  @Autowired
  public UsersController(UserService userService, ModelMapper mapper) {
    _userService = userService;
    _mapper = mapper;
  }

  @GetMapping("{groupId}/users")
  public ResponseEntity<Collection<User>> getUsers(@PathVariable String groupId) {
    return ResponseEntity.ok(_userService.getByGroup(groupId));
  }

  @GetMapping("{groupId}/users/{userId}")
  public ResponseEntity<User> getUser(@PathVariable String userId) {
    return ResponseEntity.ok(_userService.get(userId));
  }

  @PostMapping("{groupId}/users")
  public ResponseEntity<User> createUser(@PathVariable String groupId, @RequestBody CreateUserRequest request) {
    var user = _mapper.map(request, User.class);
    user.setGroupId(groupId);

    return ResponseEntity.ok(_userService.create(user));
  }

  @PutMapping("{groupId}/users/{userId}")
  public ResponseEntity<User> updateUser(@PathVariable String groupId, @PathVariable String userId, @RequestBody UpdateUserRequest request) {
    var user = _mapper.map(request, User.class);
    user.setId(userId);
    user.setGroupId(groupId);

    return ResponseEntity.ok(_userService.update(user));
  }

  @PutMapping("{groupId}/users/{userId}/password")
  public ResponseEntity<User> changePasswordForUser(@PathVariable String userId, @RequestBody ChangePasswordRequest request) {
    return ResponseEntity.ok(_userService.changePassword(userId, request.getCurrentPassword(), request.getNewPassword()));
  }

  @DeleteMapping("{groupId}/users/{userId}")
  public ResponseEntity<User> deleteUser(@PathVariable String userId) {
    return ResponseEntity.ok(_userService.delete(userId));
  }
  
}
