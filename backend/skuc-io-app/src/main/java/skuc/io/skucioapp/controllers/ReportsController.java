package skuc.io.skucioapp.controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.drools.core.ClassObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skuciocore.ksessions.SessionManager;
import skuc.io.skuciocore.models.reports.AtSomeTimeReportFilters;
import skuc.io.skuciocore.models.reports.CollectReport;
import skuc.io.skuciocore.models.reports.MaxPeriodReportFilters;
import skuc.io.skuciocore.models.reports.PredefinedReportResult;
import skuc.io.skuciocore.models.reports.ReportFilters;
import skuc.io.skuciocore.models.reports.ReportResult;
import skuc.io.skuciocore.services.PredefinedReportsService;

@RestController
@RequestMapping("groups")
public class ReportsController {
  
  private final PredefinedReportsService _predefinedReportsService;
  private final SessionManager _sessionManager;

  @Autowired
  public ReportsController(
    PredefinedReportsService predefinedReportsService,
    SessionManager sessionManager
  ) {
    _predefinedReportsService = predefinedReportsService;
    _sessionManager = sessionManager;
  }

  @PostMapping("{groupId}/locations/{locationId}/reports/normal")
  public ResponseEntity<ReportResult> getNormalReport(@PathVariable String locationId, @RequestBody ReportFilters reportFilters) {

    var reportSession = _sessionManager.getReportSession(locationId);
    reportSession.insert(reportFilters);
    reportSession.fireAllRules();

    reportSession.insert(new CollectReport());
    reportSession.fireAllRules();

    var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
    reportSession.dispose();

    return ResponseEntity.ok(reportResult);
  }

  @PostMapping("{groupId}/locations/{locationId}/reports/at-some-time")
  public ResponseEntity<ReportResult> getAtsomeTimeReport(@PathVariable String locationId, @RequestBody AtSomeTimeReportFilters reportFilters) {

    var reportSession = _sessionManager.getReportSession(locationId);
    reportSession.insert(reportFilters);
    reportSession.fireAllRules();

    reportSession.insert(new CollectReport());
    reportSession.fireAllRules();

    var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
    reportSession.dispose();

    return ResponseEntity.ok(reportResult);
  }

  @PostMapping("{groupId}/locations/{locationId}/reports/max-period")
  public ResponseEntity<ReportResult> getMaxPeriodReport(@PathVariable String locationId, @RequestBody MaxPeriodReportFilters reportFilters) {

    var reportSession = _sessionManager.getReportSession(locationId);
    reportSession.insert(reportFilters);
    reportSession.fireAllRules();

    reportSession.insert(new CollectReport());
    reportSession.fireAllRules();

    var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
    reportSession.dispose();

    return ResponseEntity.ok(reportResult);
  }

  @PostMapping("{groupId}/locations/reports/predefined")
  public ResponseEntity<Collection<PredefinedReportResult>> getReportsByPredefined(@PathVariable String groupId) {

    var results = new ArrayList<PredefinedReportResult>();
    var predefinedReports = _predefinedReportsService.getByGroup(groupId);
    
    for (var predefinedReport : predefinedReports) {
      var reportSession = _sessionManager.getReportSession(predefinedReport.getLocationId());
      reportSession.insert(predefinedReport.getFilter());
      reportSession.fireAllRules();

      reportSession.insert(new CollectReport());
      reportSession.fireAllRules();

      var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
      reportSession.dispose();

      results.add(new PredefinedReportResult(reportResult, predefinedReport.getType(), predefinedReport.getName()));
    }

    return ResponseEntity.ok(results);
  }

}
