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

import skuc.io.skucioapp.api_contracts.requests.Activators.CreateEventActivatorRequest;
import skuc.io.skucioapp.api_contracts.requests.Activators.CreateTimePeriodActivatorRequest;
import skuc.io.skucioapp.api_contracts.requests.Activators.UpdateEventActivatorRequest;
import skuc.io.skucioapp.api_contracts.requests.Activators.UpdateTimePeriodActivatorRequest;
import skuc.io.skuciocore.models.csm.context.activation.EventActivator;
import skuc.io.skuciocore.models.csm.context.activation.TimePeriodActivator;
import skuc.io.skuciocore.services.EventActivatorService;
import skuc.io.skuciocore.services.TimePeriodActivatorService;

@RestController
@RequestMapping("activators")
public class ActivatorsController {

  private final EventActivatorService _eventActivatorService;
  private final TimePeriodActivatorService _timePeriodActivatorService;
  private final ModelMapper _mapper;

  @Autowired
  public ActivatorsController(EventActivatorService eventActivatorService, TimePeriodActivatorService timePeriodActivatorService, ModelMapper mapper) {
    _eventActivatorService = eventActivatorService;
    _timePeriodActivatorService = timePeriodActivatorService;
    _mapper = mapper;
  }

  @GetMapping("{contextId}/event")
  public ResponseEntity<Collection<EventActivator>> getEventActivators(@PathVariable String contextId) {
    return ResponseEntity.ok(_eventActivatorService.getByContext(contextId));
  }

  @GetMapping("{contextId}/event/{eventActivatorId}")
  public ResponseEntity<EventActivator> getEventActivator(@PathVariable String contextId,
      @PathVariable String eventActivatorId) {
    return ResponseEntity.ok(_eventActivatorService.getOrThrow(eventActivatorId));
  }

  @PostMapping("{contextId}/event")
  public ResponseEntity<EventActivator> createEventActivator(@PathVariable String contextId,
      @RequestBody CreateEventActivatorRequest request) {
    var eventActivator = _mapper.map(request, EventActivator.class);
    eventActivator.setContextId(contextId);

    return ResponseEntity.ok(_eventActivatorService.create(eventActivator));
  }

  @PutMapping("{contextId}/event/{eventActivatorId}")
  public ResponseEntity<EventActivator> updateEventActivator(@PathVariable String contextId,
      @PathVariable String eventActivatorId, @RequestBody UpdateEventActivatorRequest request) {
    var eventActivator = _mapper.map(request, EventActivator.class);
    eventActivator.setId(eventActivatorId);
    eventActivator.setContextId(contextId);

    return ResponseEntity.ok(_eventActivatorService.update(eventActivator));
  }

  @DeleteMapping("{contextId}/event/{eventActivatorId}")
  public ResponseEntity<EventActivator> deleteEventActivator(@PathVariable String eventActivatorId) {
    return ResponseEntity.ok(_eventActivatorService.delete(eventActivatorId));
  }

  @GetMapping("{contextId}/time-period")
  public ResponseEntity<Collection<TimePeriodActivator>> getTimePeriodActivators(@PathVariable String contextId) {
    return ResponseEntity.ok(_timePeriodActivatorService.getByContext(contextId));
  }

  @GetMapping("{contextId}/time-period/{timePeriodActivatorId}")
  public ResponseEntity<TimePeriodActivator> getTimePeriodActivator(@PathVariable String contextId,
      @PathVariable String timePeriodActivatorId) {
    return ResponseEntity.ok(_timePeriodActivatorService.getOrThrow(timePeriodActivatorId));
  }

  @PostMapping("{contextId}/time-period")
  public ResponseEntity<TimePeriodActivator> createTimePeriodActivator(@PathVariable String contextId,
      @RequestBody CreateTimePeriodActivatorRequest request) {
    var timePeriodActivator = _mapper.map(request, TimePeriodActivator.class);
    timePeriodActivator.setContextId(contextId);

    return ResponseEntity.ok(_timePeriodActivatorService.create(timePeriodActivator));
  }

  @PutMapping("{contextId}/time-period/{timePeriodActivatorId}")
  public ResponseEntity<TimePeriodActivator> updateTimePeriodActivator(@PathVariable String contextId,
      @PathVariable String timePeriodActivatorId, @RequestBody UpdateTimePeriodActivatorRequest request) {
    var timePeriodActivator = _mapper.map(request, TimePeriodActivator.class);
    timePeriodActivator.setId(timePeriodActivatorId);
    timePeriodActivator.setContextId(contextId);

    return ResponseEntity.ok(_timePeriodActivatorService.update(timePeriodActivator));
  }

  @DeleteMapping("{contextId}/time-period/{timePeriodActivatorId}")
  public ResponseEntity<TimePeriodActivator> deleteTimePeriodActivator(@PathVariable String timePeriodActivatorId) {
    return ResponseEntity.ok(_timePeriodActivatorService.delete(timePeriodActivatorId));
  }

}
