package skuc.io.skucioapp.controllers;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skuc.io.skuciocore.models.Test;


@RestController
public class TestController {
  
  private final KieContainer kieContainer;


  @Autowired
  public TestController(KieContainer kieContainer) {
    super();
    this.kieContainer = kieContainer;
  }

  @RequestMapping(value = "/tes", method = RequestMethod.GET, produces = "application/json")
	public Test getQuestions() {
		Test newTest = new Test();

    KieSession kieSession = kieContainer.newKieSession();
    kieSession.insert(newTest);
    kieSession.fireAllRules();
    kieSession.dispose();

		return newTest;
	}
}
