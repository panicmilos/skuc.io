package skuc.io.skuciocore.ksessions;

import java.util.HashMap;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import skuc.io.skuciocore.services.ContextService;

@Component
public class SessionManager {
  
  private static HashMap<String, KieSession> _sessions = new HashMap<>();
  private final KieContainer _kieContainer;
  private final ContextService _contextService;
  
  @Autowired
  public SessionManager(KieContainer kieContainer, ContextService contextService) {
    _kieContainer = kieContainer;
    _contextService = contextService;
  } 


  public KieSession getSession(String key) {
    if (!_sessions.containsKey(key)) {
      var session = _kieContainer.newKieSession();
      var contexts = _contextService.get();
      for (var context : contexts) {
        session.insert(context);
      }

      _sessions.put(key, session);
    }

    return _sessions.get(key);
  }

}