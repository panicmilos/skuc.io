package skuc.io.skuciocore.ksessions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.filefilter.CanWriteFileFilter;
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
import skuc.io.skuciocore.models.events.device.Aggregate;
// TODO: REMOVE THIS
import skuc.io.skuciocore.models.events.device.Value0005Aggregated;
import skuc.io.skuciocore.models.events.device.Value0015Aggregated;
import skuc.io.skuciocore.models.events.device.Value0030Aggregated;
import skuc.io.skuciocore.models.events.device.Value0060Aggregated;
import skuc.io.skuciocore.models.reports.LocalDateTimePeriod;
import skuc.io.skuciocore.models.reports.ParamFilter;
import skuc.io.skuciocore.models.reports.ReportFilters;
import skuc.io.skuciocore.models.reports.ValueFilter;


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
  
  private final List<Object> facts = new ArrayList<>();

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

      var contexts = _contextService.getActiveContextsFor(group.getId(), location.getId());
      for (var context : contexts) {
        session.insert(context);
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

  public void insertToAllSessions(Object object) {
    for(var session : _sessions.values()) {
      session.insert(object);
      session.fireAllRules();
    }
  }

  public void closeSession(String key) {
    var session = _sessions.get(key);

    session.dispose();
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

  private final String DEVICE_ID = "953164df-0432-43e7-b735-a204dfcda3ed";
  private final String DEVICE_TYPE = "Temperature Sensor";
  private final String PARAM_NAME = "temp";
  private void insertValue0005Aggregated(KieSession _session, String parentId, String id, Double min, Double max, Double sum, Double average, Long count) {
    _session.insert((new Value0005Aggregated(parentId, id, DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }

  private void insertValue0015Aggregated(KieSession _session, String parentId, String id, Double min, Double max, Double sum, Double average, Long count) {
    _session.insert((new Value0015Aggregated(parentId, id, DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }

  private void insertValue0030Aggregated(KieSession _session, String parentId, String id, Double min, Double max, Double sum, Double average, Long count) {
    _session.insert((new Value0030Aggregated(parentId, id, DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }

  private void insertValue0060Aggregated(KieSession _session, String parentId, String id, Double min, Double max, Double sum, Double average, Long count) {
    _session.insert((new Value0060Aggregated(parentId, id, DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }


  public KieSession getAggregateSession() {
    if (!_sessions.containsKey("aggregation_session")) {
      var session = _kieContainer.newKieSession("DefaultCepSession");

      insertValue0005Aggregated(session, "4", "0", 9D, 22D, 44D, 12D, 4L);
      insertValue0005Aggregated(session, "5", "1", 10D, 22D, 44D, 11D, 4L);
      insertValue0005Aggregated(session, "5", "2", 2D, 33D, 100D, 20D, 5L);
      insertValue0005Aggregated(session, "5", "3", 1D, 1D, 1D, 1D, 1L);
      insertValue0015Aggregated(session, "7", "4", 3D, 13D, 24D, 7D, 3L);
      insertValue0015Aggregated(session, "8", "5", 6D, 11D, 25D, 6.25D, 4L);
      insertValue0015Aggregated(session, "8", "6", 3D, 13D, 24D, 7D, 3L);
      insertValue0030Aggregated(session, "9", "7", 7D, 56D, 96D, 16D, 6L);  
      insertValue0030Aggregated(session, "9", "8", 11D, 13D, 24D, 12D, 2L);
      insertValue0060Aggregated(session, "", "9", 1D, 14D, 22D, 5.25D, 4L);

      new AggregationThread("AggregationThread", session).start();
      
      _sessions.put("aggregation_session", session);
    }

    return _sessions.get("aggregation_session");
  }

  public KieSession getReportSession() {
    var aggregationSession = getAggregateSession();

    var aggregatedObjects = aggregationSession.getObjects();
    facts.addAll(aggregatedObjects);

    var reportSession = _kieContainer.newKieSession("ReportSession");
    for(var object : facts) {
      reportSession.insert(object);
    }

    var param1 = new ParamFilter("temp", "average", new ArrayList<ValueFilter>() {{
      add(new ValueFilter("!=", "min", 11D));
      add(new ValueFilter(">=", "min", 6D));
    }});
    var param2 = new ParamFilter("temp", "min", new ArrayList<ValueFilter>() {{
      add(new ValueFilter("!=", "min", 11D));
      add(new ValueFilter(">=", "min", 6D));
    }});
    var period = new LocalDateTimePeriod(LocalDateTime.now().minusMinutes(5), null);

    reportSession.insert(new ReportFilters(5, period, new ArrayList<>() {{ add(param1); add(param2); }}));

    return reportSession;
  }

}