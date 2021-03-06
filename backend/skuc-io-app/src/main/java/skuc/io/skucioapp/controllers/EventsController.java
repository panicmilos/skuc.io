package skuc.io.skucioapp.controllers;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.api_contracts.requests.Events.StatusReceivedRequest;
import skuc.io.skucioapp.api_contracts.requests.Events.ValueReceivedRequest;
import skuc.io.skuciocore.ksessions.SessionManager;
import skuc.io.skuciocore.models.csm.StateRegistry;
import skuc.io.skuciocore.models.events.device.StatusReceived;
import skuc.io.skuciocore.models.events.device.ValueReceived;
import skuc.io.skuciocore.models.events.kjar.ActivateContextByName;
import skuc.io.skuciocore.models.events.kjar.ValueReceivedCopy;
import skuc.io.skuciocore.services.DeviceService;
import skuc.io.skuciocore.services.NotificationService;
import skuc.io.skuciocore.services.StateRegistryService;
import skuc.io.skuciocore.services.events.StatusReceivedService;
import skuc.io.skuciocore.services.events.ValueReceivedService;

@RestController
@RequestMapping("events")
public class EventsController {

  private final ValueReceivedService _service;
  private final StatusReceivedService _statusService;
  private final DeviceService _deviceService;
  private final SessionManager _sessionManager;
  private final NotificationService _nofiticationService;
  private final ModelMapper _mapper;
  private final StateRegistryService _stateRegistryService;

  @Autowired
  public EventsController(
    ValueReceivedService service,
    StatusReceivedService statusService,
    DeviceService deviceService,
    SessionManager sessionManager,
    NotificationService nofiticationService,
    StateRegistryService stateRegistryService,
    ModelMapper mapper
  ) {
    _service = service;
    _statusService = statusService;
    _deviceService = deviceService;
    _sessionManager = sessionManager;
    _nofiticationService = nofiticationService;
    _stateRegistryService = stateRegistryService;
    _mapper = mapper;
  }

  @GetMapping("state")
  public ResponseEntity<StateRegistry> getStatusesRegistry(@RequestParam String locationId) {

    return ResponseEntity.ok(_stateRegistryService.getStateRegistryFor(locationId));
  }

  @PostMapping("values")
  public ResponseEntity<ValueReceived> createValue(@RequestBody ValueReceivedRequest request) {
    var valueReceived = _mapper.map(request, ValueReceived.class);
    valueReceived.setId(UUID.randomUUID().toString());

    var device = _deviceService.getByDevice(valueReceived.getDeviceId());
    
    _nofiticationService.sendFrom(valueReceived);
    
    var aggregationSession = _sessionManager.getAggregateSession(device.getLocationId().toString());
    aggregationSession.insert(valueReceived);

    var session = _sessionManager.getSession(device.getLocationId().toString());
    session.insert(valueReceived);
    session.insert(new ValueReceivedCopy(valueReceived));
    session.fireAllRules();


    return ResponseEntity.ok(_service.create(valueReceived));
  }

  @PostMapping("statuses")
  public ResponseEntity<StatusReceived> createStatus(@RequestBody StatusReceivedRequest request) {
    var statusReceived = _mapper.map(request, StatusReceived.class);
    statusReceived.setId(UUID.randomUUID().toString());

    var device = _deviceService.getByDevice(statusReceived.getDeviceId());

    _nofiticationService.sendFrom(statusReceived);
    
    var session = _sessionManager.getSession(device.getLocationId().toString());
    session.insert(statusReceived);
    session.fireAllRules();


    return ResponseEntity.ok(_statusService.create(statusReceived));
  }

  @PostMapping("test-active")
  public ResponseEntity<ValueReceived> createValue() {

    var session = _sessionManager.getSession("6c31995f-1f79-48a4-a4a4-2e5af235e2de");
    session.insert(new ActivateContextByName("AH"));
    session.fireAllRules();

    System.out.println(session.getFactCount());

    return ResponseEntity.ok(null);
  }

  
}
