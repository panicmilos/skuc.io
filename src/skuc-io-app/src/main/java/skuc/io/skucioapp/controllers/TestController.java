package skuc.io.skucioapp.controllers;

import java.util.UUID;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.api_contracts.requests.CreateUserRequest;
import skuc.io.skuciocore.models.csm.User;
import skuc.io.skuciocore.services.UserService;


@RestController
public class TestController {
  
  private final UserService _userService;
  private final KieContainer _kieContainer;
  private final ModelMapper _mapper;

  @Autowired
  public TestController(UserService userService, ModelMapper mapper, KieContainer kieContainer) {
    _userService = userService;
    _mapper = mapper;
    _kieContainer = kieContainer;
  }    


  @GetMapping("test")
	public User fireRule() {
		User newUser = new User();

    KieSession kieSession = _kieContainer.newKieSession();
    kieSession.insert(newUser);
    kieSession.fireAllRules();
    kieSession.dispose();

		return newUser;
	}

  @GetMapping("/{id}")
	public User getUser(@PathVariable UUID id) {

    return _userService.getOrThrow(id);
	}

  @PostMapping("create")
	public User createUser(@RequestBody CreateUserRequest request) {

    var user = _mapper.map(request, User.class);
    _userService.create(user);

		return user;
	}
}
