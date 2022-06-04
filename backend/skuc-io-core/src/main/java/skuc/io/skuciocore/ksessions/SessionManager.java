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
import skuc.io.skuciocore.services.EventDeactivatorService;
import skuc.io.skuciocore.services.GroupService;
import skuc.io.skuciocore.services.LocationService;
import skuc.io.skuciocore.services.NotificationService;
import skuc.io.skuciocore.services.StateRegistryService;
import skuc.io.skuciocore.services.TimePeriodActivatorService;
import skuc.io.skuciocore.services.TimePeriodDeactivatorService;
import skuc.io.skuciocore.services.events.ContextActivatedService;
import skuc.io.skuciocore.services.events.ContextDeactivatedService;

@Component
public class SessionManager {
  
  private static HashMap<String, KieSession> _sessions = new HashMap<>();
  private final KieContainer _kieContainer;

  private final GroupService _groupService;
  private final ContextService _contextService;

  private final EventActivatorService _eventActivatorService;
  private final TimePeriodActivatorService _timePeriodActivatorService;
  private final ContextActivatedService _contextActivatedService;

  private final EventDeactivatorService _eventDeactivatorService;
  private final TimePeriodDeactivatorService _timePeriodDeactivatorService;
  private final ContextDeactivatedService _contextDeactivatedService;

  private final LocationService _locationService;
  private final NotificationService _notificationService;
  private final StateRegistryService _stateRegistryService;
  
  
  @Autowired
  public SessionManager(
    KieContainer kieContainer,
    GroupService groupService,
    ContextService contextService,

    EventActivatorService eventActivatorService,
    TimePeriodActivatorService timePeriodActivatorService,
    ContextActivatedService contextActivatedService,
    
    EventDeactivatorService eventDeactivatorService,
    TimePeriodDeactivatorService timePeriodDeactivatorService,
    ContextDeactivatedService contextDeactivatedService,

    LocationService locationService,
    NotificationService notificationService,
    StateRegistryService stateRegistryService
  ) {
    _kieContainer = kieContainer;

    _groupService = groupService;
    _contextService = contextService;

    _eventActivatorService = eventActivatorService;
    _timePeriodActivatorService = timePeriodActivatorService;
    _contextActivatedService = contextActivatedService;

    _eventDeactivatorService = eventDeactivatorService;
    _timePeriodDeactivatorService = timePeriodDeactivatorService;
    _contextDeactivatedService = contextDeactivatedService;

    _locationService = locationService;
    _notificationService = notificationService;
    _stateRegistryService = stateRegistryService;
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

      session.setGlobal("eventDeactivatorService", _eventDeactivatorService);
      session.setGlobal("timePeriodDeactivatorService", _timePeriodDeactivatorService);
      session.setGlobal("contextDeactivatedService", _contextDeactivatedService);

      session.setGlobal("notificationService", _notificationService);
      
      session.setGlobal("stateRegistryService", _stateRegistryService);

      var stateRegistry = _stateRegistryService.getStateRegistryFor(key);
      stateRegistry.setState("test", "test");
      session.insert(stateRegistry);

      _sessions.put(key, session);
    }

    return _sessions.get(key);
  }

  private class AggregationThread extends Thread {
    private final KieSession _kieSession;
    public AggregationThread(String name, KieSession kieSession) {
      super(name);
      _kieSession = kieSession;

      setDaemon(true);
    }

    public void run() {
      _kieSession.fireUntilHalt();
    }
  }

  public KieSession getAggregateSession() {
    if (!_sessions.containsKey("aggregation_session")) {
      var session = _kieContainer.getKieBase("CepKBase").newKieSession();

      new AggregationThread("AggregationThread", session).start();
      
      _sessions.put("aggregation_session", session);
    }

    return _sessions.get("aggregation_session");

  }

}