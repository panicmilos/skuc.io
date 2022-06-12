package skuc.io.skucioapp.controllers;

import java.util.Collection;
import java.util.UUID;

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

import skuc.io.skucioapp.api_contracts.requests.PredefinedReports.CreatePredefinedReportRequest;
import skuc.io.skucioapp.api_contracts.requests.PredefinedReports.UpdatePredefinedReportRequest;
import skuc.io.skuciocore.models.reports.PredefinedReport;
import skuc.io.skuciocore.services.PredefinedReportsService;

@RestController
@RequestMapping("groups")
public class PredefinedReportsController {
  

  private final PredefinedReportsService _predefinedReportsService;
  private final ModelMapper _mapper;

  @Autowired
  public PredefinedReportsController(PredefinedReportsService predefinedReportsService, ModelMapper mapper) {
    super();
    _predefinedReportsService = predefinedReportsService;
    _mapper = mapper;
  }

  @GetMapping("{groupId}/locations/predefined-reports")
  public ResponseEntity<Collection<PredefinedReport>> getPredefinedReports(@PathVariable String groupId) {
    return ResponseEntity.ok(_predefinedReportsService.getByGroup(groupId));
  }

  @GetMapping("{groupId}/locations/predefined-reports/{predefinedReportId}")
  public ResponseEntity<PredefinedReport> getPredefinedReport(@PathVariable String predefinedReportId) {
    return ResponseEntity.ok(_predefinedReportsService.getOrThrow(predefinedReportId));
  }

  @PostMapping("{groupId}/locations/{locationId}/predefined-reports")
  public ResponseEntity<PredefinedReport> createPredefinedReport(@PathVariable String groupId, @PathVariable String locationId, @RequestBody CreatePredefinedReportRequest request) {
    var predefinedReport = _mapper.map(request, PredefinedReport.class);
    predefinedReport.setId(UUID.randomUUID().toString());
    predefinedReport.setGroupId(groupId);
    predefinedReport.setLocationId(locationId);

    return ResponseEntity.ok(_predefinedReportsService.create(predefinedReport));
  }

  @PutMapping("{groupId}/locations/{locationId}/predefined-reports/{predefinedReportId}")
  public ResponseEntity<PredefinedReport> updatePredefinedReport(@PathVariable String groupId, @PathVariable String locationId, @PathVariable String predefinedReportId, @RequestBody UpdatePredefinedReportRequest request) {
    var predefinedReport = _mapper.map(request, PredefinedReport.class);
    predefinedReport.setId(predefinedReportId);
    predefinedReport.setGroupId(groupId);
    predefinedReport.setLocationId(locationId);

    return ResponseEntity.ok(_predefinedReportsService.update(predefinedReport));
  }

  @DeleteMapping("{groupId}/locations/{locationId}/predefined-reports/{predefinedReportId}")
  public ResponseEntity<PredefinedReport> deletePredefinedReport(@PathVariable String predefinedReportId) {
    return ResponseEntity.ok(_predefinedReportsService.delete(predefinedReportId));
  }



}
