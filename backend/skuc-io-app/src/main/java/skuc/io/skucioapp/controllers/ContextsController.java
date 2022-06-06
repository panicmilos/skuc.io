package skuc.io.skucioapp.controllers;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.api_contracts.requests.Contexts.ActivateContextRequest;
import skuc.io.skucioapp.api_contracts.requests.Contexts.CreateContextRequest;
import skuc.io.skucioapp.api_contracts.requests.Contexts.DeactivateContextRequest;
import skuc.io.skucioapp.api_contracts.requests.Contexts.UpdateContextRequest;
import skuc.io.skuciocore.ksessions.SessionManager;
import skuc.io.skuciocore.models.csm.configuration.Context;
import skuc.io.skuciocore.models.events.kjar.ActivateContextById;
import skuc.io.skuciocore.models.events.kjar.ContextDeleted;
import skuc.io.skuciocore.models.events.kjar.ContextUpdated;
import skuc.io.skuciocore.models.events.kjar.DeactivateContextById;
import skuc.io.skuciocore.services.ContextService;
import skuc.io.skuciocore.services.EventActivatorService;
import skuc.io.skuciocore.services.EventDeactivatorService;
import skuc.io.skuciocore.services.TimePeriodActivatorService;
import skuc.io.skuciocore.services.TimePeriodDeactivatorService;

@RestController
@RequestMapping("groups")
public class ContextsController {
  
  private final ContextService _contextService;
  private final EventActivatorService _eventActivatorService;
  private final EventDeactivatorService _eventDeactivatorService;
  private final TimePeriodActivatorService _timePeriodActivatorService;
  private final TimePeriodDeactivatorService _timePeriodDeactivatorService;
  private final SessionManager _manager;
  private final ModelMapper _mapper;

  @Autowired
  public ContextsController(
    EventActivatorService eventActivatorService,
    EventDeactivatorService eventDeactivatorService,
    TimePeriodActivatorService timePeriodActivatorService,
    TimePeriodDeactivatorService timePeriodDeactivatorService,
    ContextService contextService,
    SessionManager manager,
    ModelMapper mapper
  ) {
    _contextService = contextService;
    _eventActivatorService = eventActivatorService;
    _eventDeactivatorService = eventDeactivatorService;
    _timePeriodActivatorService = timePeriodActivatorService;
    _timePeriodDeactivatorService = timePeriodDeactivatorService;
    _manager = manager;
    _mapper = mapper;
  }

  @GetMapping("{groupId}/contexts")
  public ResponseEntity<Collection<Context>> getContexts(@PathVariable String groupId) {
    return ResponseEntity.ok(_contextService.getByGroup(groupId));
  }

  @GetMapping("{groupId}/contexts/{contextId}")
  public ResponseEntity<Context> getContext(@PathVariable String contextId) {
    return ResponseEntity.ok(_contextService.getOrThrow(contextId));
  }

  @PostMapping("{groupId}/contexts")
  public ResponseEntity<Context> createContext(@PathVariable String groupId, @RequestBody CreateContextRequest request) {
    var context = _mapper.map(request, Context.class);
    context.setGroupId(groupId);

    return ResponseEntity.ok(_contextService.create(context));
  }

  @PutMapping("{groupId}/contexts/{contextId}")
  public ResponseEntity<Context> updateContext(@PathVariable String groupId, @PathVariable String contextId, @RequestBody UpdateContextRequest request) {
    var context = _mapper.map(request, Context.class);
    context.setId(contextId);
    context.setGroupId(groupId);

    var updatedContext = _contextService.update(context);

    _manager.insertToAllSessions(new ContextUpdated(updatedContext.getId()));

    return ResponseEntity.ok(updatedContext);
  }

  @DeleteMapping("{groupId}/contexts/{contextId}")
  public ResponseEntity<Context> deleteContext(@PathVariable String contextId) {
    var deletedContext = _contextService.delete(contextId);

    _eventActivatorService.deleteByContext(deletedContext.getId());
    _eventDeactivatorService.deleteByContext(deletedContext.getId());
    _timePeriodActivatorService.deleteByContext(deletedContext.getId());
    _timePeriodDeactivatorService.deleteByContext(deletedContext.getId());
    _manager.insertToAllSessions(new ContextDeleted(deletedContext.getId()));

    return ResponseEntity.ok(deletedContext);
  }

  @PostMapping("{groupId}/contexts/{contextId}/activate")
  public ResponseEntity<Context> activateContext(@PathVariable String contextId, @RequestBody ActivateContextRequest request) {

    var session = _manager.getSession(request.getLocationId());

    session.insert(new ActivateContextById(contextId));
    session.fireAllRules();

    return ResponseEntity.ok(null);
  }

  @PostMapping("{groupId}/contexts/{contextId}/deactivate")
  public ResponseEntity<Context> deactivateContext(@PathVariable String contextId, @RequestBody DeactivateContextRequest request) {

    var session = _manager.getSession(request.getLocationId());

    session.insert(new DeactivateContextById(contextId));
    session.fireAllRules();

    return ResponseEntity.ok(null);
  }

}
