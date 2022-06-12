package skuc.io.skucioapp.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.drools.core.ClassObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("reports")
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

  @PostMapping("normal")
  public ResponseEntity<ReportResult> getNormalReport(@RequestBody ReportFilters reportFilters) {

    var reportSession = _sessionManager.getReportSession();
    reportSession.insert(reportFilters);
    reportSession.fireAllRules();

    reportSession.insert(new CollectReport());
    reportSession.fireAllRules();

    var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
    reportSession.dispose();

    return ResponseEntity.ok(reportResult);
  }

  @PostMapping("at-some-time")
  public ResponseEntity<ReportResult> getAtsomeTimeReport(@RequestBody AtSomeTimeReportFilters reportFilters) {

    var reportSession = _sessionManager.getReportSession();
    reportSession.insert(reportFilters);
    reportSession.fireAllRules();

    reportSession.insert(new CollectReport());
    reportSession.fireAllRules();

    var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
    reportSession.dispose();

    return ResponseEntity.ok(reportResult);
  }

  @PostMapping("max-period")
  public ResponseEntity<ReportResult> getMaxPeriodReport(@RequestBody MaxPeriodReportFilters reportFilters) {

    var reportSession = _sessionManager.getReportSession();
    reportSession.insert(reportFilters);
    reportSession.fireAllRules();

    reportSession.insert(new CollectReport());
    reportSession.fireAllRules();

    var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
    reportSession.dispose();

    return ResponseEntity.ok(reportResult);
  }

  @PostMapping("predefined")
  public ResponseEntity<Collection<PredefinedReportResult>> getReportsByPredefined() {

    var results = new ArrayList<PredefinedReportResult>();
    var predefinedReports = _predefinedReportsService.getByGroup("7bc3aca6-ddd4-43c3-a2ab-88d979f72c01");
    
    for (var predefinedReport : predefinedReports) {
      var reportSession = _sessionManager.getReportSession();
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
