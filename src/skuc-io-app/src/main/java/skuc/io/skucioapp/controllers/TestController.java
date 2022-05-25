package skuc.io.skucioapp.controllers;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skuciocore.models.csm.User;


@RestController
public class TestController {
  
  private final KieContainer _kieContainer;

  @Autowired
  public TestController(KieContainer kieContainer) {
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


}
