package skuc.io.skucioapp.controllers;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skuciocore.models.csm.User;


@RestController
public class TestController {
  
  private final KieContainer kieContainer;


  @Autowired
  public TestController(KieContainer kieContainer) {
    super();
    this.kieContainer = kieContainer;
  }

  @RequestMapping(value = "/tes", method = RequestMethod.GET, produces = "application/json")
	public User getQuestions() {
		User newUser = new User();

    KieSession kieSession = kieContainer.newKieSession();
    kieSession.insert(newUser);
    kieSession.fireAllRules();
    kieSession.dispose();

		return newUser;
	}
}
