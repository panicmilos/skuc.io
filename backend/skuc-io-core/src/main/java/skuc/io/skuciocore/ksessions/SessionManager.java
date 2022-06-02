package skuc.io.skuciocore.ksessions;

import java.util.HashMap;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.conf.TimedRuleExectionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import skuc.io.skuciocore.services.ContextService;
import skuc.io.skuciocore.services.EventActivatorService;
import skuc.io.skuciocore.services.GroupService;
import skuc.io.skuciocore.services.LocationService;
import skuc.io.skuciocore.services.NotificationService;
import skuc.io.skuciocore.services.TimePeriodActivatorService;
import skuc.io.skuciocore.services.events.ContextActivatedService;

@Component
public class SessionManager {
  
  private static HashMap<String, KieSession> _sessions = new HashMap<>();
  private final KieContainer _kieContainer;

  private final GroupService _groupService;
  private final ContextService _contextService;
  private final EventActivatorService _eventActivatorService;
  private final TimePeriodActivatorService _timePeriodActivatorService;
  private final LocationService _locationService;

  private final ContextActivatedService _contextActivatedService;

  private final NotificationService _notificationService;
  
  
  @Autowired
  public SessionManager(
    KieContainer kieContainer,
    GroupService groupService,
    ContextService contextService,
    EventActivatorService eventActivatorService,
    TimePeriodActivatorService timePeriodActivatorService,
    LocationService locationService,

    ContextActivatedService contextActivatedService,

    NotificationService notificationService
  ) {
    _kieContainer = kieContainer;

    _groupService = groupService;
    _contextService = contextService;
    _eventActivatorService = eventActivatorService;
    _timePeriodActivatorService = timePeriodActivatorService;
    _locationService = locationService;
  
    _contextActivatedService = contextActivatedService;

    _notificationService = notificationService;
  }


  public KieSession getSession(String key) {

    if (!_sessions.containsKey(key)) {
      var ksconf = KieServices.Factory.get().newKieSessionConfiguration();
      ksconf.setOption( TimedRuleExectionOption.YES );
      var session = _kieContainer.newKieSession(ksconf);
      
      var location = _locationService.getOrThrow(key);
      session.insert(location);

      var group = _groupService.getOrThrow(location.getGroupId());
      session.insert(group);

      var contexts = _contextService.get();
      for (var context : contexts) {
        session.insert(context);
        break;
      }

      session.setGlobal("contextService", _contextService);
      session.setGlobal("eventActivatorService", _eventActivatorService);
      session.setGlobal("timePeriodActivatorService", _timePeriodActivatorService);

      session.setGlobal("contextActivatedService", _contextActivatedService);

      session.setGlobal("notificationService", _notificationService);

      _sessions.put(key, session);
    }

    return _sessions.get(key);
  }

}