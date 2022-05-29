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

import skuc.io.skucioapp.api_contracts.requests.Deactivators.CreateEventDeactivatorRequest;
import skuc.io.skucioapp.api_contracts.requests.Deactivators.CreateTimePeriodDeactivatorRequest;
import skuc.io.skucioapp.api_contracts.requests.Deactivators.UpdateEventDeactivatorRequest;
import skuc.io.skucioapp.api_contracts.requests.Deactivators.UpdateTimePeriodDeactivatorRequest;
import skuc.io.skuciocore.models.csm.context.deactivation.EventDeactivator;
import skuc.io.skuciocore.models.csm.context.deactivation.TimePeriodDeactivator;
import skuc.io.skuciocore.services.EventDeactivatorService;
import skuc.io.skuciocore.services.TimePeriodDeactivatorService;

@RestController
@RequestMapping("deactivators")
public class DeactivatorsController {

  private final EventDeactivatorService _eventDeactivatorService;
  private final TimePeriodDeactivatorService _timePeriodDeactivatorService;
  private final ModelMapper _mapper;

  @Autowired
  public DeactivatorsController(EventDeactivatorService eventDeactivatorService, TimePeriodDeactivatorService timePeriodDeactivatorService, ModelMapper mapper) {
    _eventDeactivatorService = eventDeactivatorService;
    _timePeriodDeactivatorService = timePeriodDeactivatorService;
    _mapper = mapper;
  }

  @GetMapping("{contextId}/event")
  public ResponseEntity<Collection<EventDeactivator>> getEventDeactivators(@PathVariable String contextId) {
    return ResponseEntity.ok(_eventDeactivatorService.getByContext(contextId));
  }

  @GetMapping("{contextId}/event/{eventDeactivatorId}")
  public ResponseEntity<EventDeactivator> getEventDeactivator(@PathVariable String contextId,
      @PathVariable String eventDeactivatorId) {
    return ResponseEntity.ok(_eventDeactivatorService.getOrThrow(eventDeactivatorId));
  }

  @PostMapping("{contextId}/event")
  public ResponseEntity<EventDeactivator> createEventDeactivator(@PathVariable String contextId,
      @RequestBody CreateEventDeactivatorRequest request) {
    var eventDeactivator = _mapper.map(request, EventDeactivator.class);
    eventDeactivator.setContextId(contextId);

    return ResponseEntity.ok(_eventDeactivatorService.create(eventDeactivator));
  }

  @PutMapping("{contextId}/event/{eventDeactivatorId}")
  public ResponseEntity<EventDeactivator> updateEventDeactivator(@PathVariable String contextId,
      @PathVariable String eventDeactivatorId, @RequestBody UpdateEventDeactivatorRequest request) {
    var eventDeactivator = _mapper.map(request, EventDeactivator.class);
    eventDeactivator.setId(eventDeactivatorId);
    eventDeactivator.setContextId(contextId);

    return ResponseEntity.ok(_eventDeactivatorService.update(eventDeactivator));
  }

  @DeleteMapping("{contextId}/event/{eventDeactivatorId}")
  public ResponseEntity<EventDeactivator> deleteEventDeactivator(@PathVariable String eventDeactivatorId) {
    return ResponseEntity.ok(_eventDeactivatorService.delete(eventDeactivatorId));
  }

  
  @GetMapping("{contextId}/time-period")
  public ResponseEntity<Collection<TimePeriodDeactivator>> getTimePeriodDeactivators(@PathVariable String contextId) {
    return ResponseEntity.ok(_timePeriodDeactivatorService.getByContext(contextId));
  }

  @GetMapping("{contextId}/time-period/{timePeriodDeactivatorId}")
  public ResponseEntity<TimePeriodDeactivator> getTimePeriodDeactivator(@PathVariable String contextId,
      @PathVariable String timePeriodDeactivatorId) {
    return ResponseEntity.ok(_timePeriodDeactivatorService.getOrThrow(timePeriodDeactivatorId));
  }

  @PostMapping("{contextId}/time-period")
  public ResponseEntity<TimePeriodDeactivator> createTimePeriodDeactivator(@PathVariable String contextId,
      @RequestBody CreateTimePeriodDeactivatorRequest request) {
    var timePeriodDeactivator = _mapper.map(request, TimePeriodDeactivator.class);
    timePeriodDeactivator.setContextId(contextId);

    return ResponseEntity.ok(_timePeriodDeactivatorService.create(timePeriodDeactivator));
  }

  @PutMapping("{contextId}/time-period/{timePeriodDeactivatorId}")
  public ResponseEntity<TimePeriodDeactivator> updateTimePeriodDeactivator(@PathVariable String contextId,
      @PathVariable String timePeriodDeactivatorId, @RequestBody UpdateTimePeriodDeactivatorRequest request) {
    var timePeriodDeactivator = _mapper.map(request, TimePeriodDeactivator.class);
    timePeriodDeactivator.setId(timePeriodDeactivatorId);
    timePeriodDeactivator.setContextId(contextId);

    return ResponseEntity.ok(_timePeriodDeactivatorService.update(timePeriodDeactivator));
  }

  @DeleteMapping("{contextId}/time-period/{timePeriodDeactivatorId}")
  public ResponseEntity<TimePeriodDeactivator> deleteTimePeriodDeactivator(@PathVariable String timePeriodDeactivatorId) {
    return ResponseEntity.ok(_timePeriodDeactivatorService.delete(timePeriodDeactivatorId));
  }

}
