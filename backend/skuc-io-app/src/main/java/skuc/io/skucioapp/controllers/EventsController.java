package skuc.io.skucioapp.controllers;

import java.text.ParseException;
import java.util.Collection;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.api_contracts.requests.Events.StatusReceivedRequest;
import skuc.io.skucioapp.api_contracts.requests.Events.ValueReceivedRequest;
import skuc.io.skuciocore.ksessions.SessionManager;
import skuc.io.skuciocore.models.csm.configuration.Context;
import skuc.io.skuciocore.models.events.device.StatusReceived;
import skuc.io.skuciocore.models.events.device.ValueReceived;
import skuc.io.skuciocore.models.events.kjar.ActivateContextByName;
import skuc.io.skuciocore.services.ContextService;
import skuc.io.skuciocore.services.DeviceService;
import skuc.io.skuciocore.services.NotificationService;
import skuc.io.skuciocore.services.events.StatusReceivedService;
import skuc.io.skuciocore.services.events.ValueReceivedService;

@RestController
@RequestMapping("events")
public class EventsController {

  private final ValueReceivedService _service;
  private final StatusReceivedService _statusService;
  private final DeviceService _deviceService;
  private final ContextService _contextService;
  private final SessionManager _sessionManager;
  private final NotificationService _nofiticationService;
  private final ModelMapper _mapper;

  @Autowired
  public EventsController(
    ValueReceivedService service,
    StatusReceivedService statusService,
    DeviceService deviceService,
    ContextService contextService,
    SessionManager sessionManager,
    NotificationService nofiticationService,
    ModelMapper mapper
  ) {
    _service = service;
    _statusService = statusService;
    _deviceService = deviceService;
    _contextService = contextService;
    _sessionManager = sessionManager;
    _nofiticationService = nofiticationService;
    _mapper = mapper;
  }

  @PostMapping("values")
  public ResponseEntity<ValueReceived> createValue(@RequestBody ValueReceivedRequest request) {
    var valueReceived = _mapper.map(request, ValueReceived.class);
    valueReceived.setId(UUID.randomUUID().toString());

    var device = _deviceService.getByDevice(valueReceived.getDeviceId());

    // var aggregation_Session = _sessionManager.getAggregateSession();
    // aggregation_Session.insert(valueReceived);

    var session = _sessionManager.getSession(device.getLocationId().toString());
    session.insert(valueReceived);
    session.fireAllRules();

    _nofiticationService.sendFrom(valueReceived);

    return ResponseEntity.ok(_service.create(valueReceived));
  }

  @PostMapping("statuses")
  public ResponseEntity<StatusReceived> createStatus(@RequestBody StatusReceivedRequest request) {
    var statusReceived = _mapper.map(request, StatusReceived.class);
    statusReceived.setId(UUID.randomUUID().toString());

    var device = _deviceService.getByDevice(statusReceived.getDeviceId());

    var session = _sessionManager.getSession(device.getLocationId().toString());
    session.insert(statusReceived);
    session.fireAllRules();

    _nofiticationService.sendFrom(statusReceived);

    return ResponseEntity.ok(_statusService.create(statusReceived));
  }

  @PostMapping("test-active")
  public ResponseEntity<ValueReceived> createValue() throws ParseException {

    var session = _sessionManager.getSession("5390ba11-857d-40c2-816a-9a2b716baa91");
    session.insert(new ActivateContextByName("Night"));
    session.fireAllRules();

    System.out.println(session.getFactCount());

    return ResponseEntity.ok(null);
  }

  @PostMapping("test-inform/{numer}")
  public ResponseEntity<ValueReceived> createValue2222(@PathVariable Number numer) throws ParseException {

    var session = _sessionManager.getSession("6c31995f-1f79-48a4-a4a4-2e5af235e2de");
    // session.insert(new ActivateContextByName("Night"));
    session.insert(numer);
    session.fireAllRules();


    System.out.println(session.getFactCount());

    return ResponseEntity.ok(null);
  }

  @GetMapping("test-active-contexts")
  public ResponseEntity<Collection<Context>> asdf() throws ParseException {
    return ResponseEntity.ok(_contextService.getActiveContextsFor("4d1297cb-9f32-46d6-84dc-4ebcd847c830", "5390ba11-857d-40c2-816a-9a2b716baa91"));
  }
  
}
