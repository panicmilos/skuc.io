package skuc.io.skucioapp.controllers;

import org.drools.core.ClassObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skuciocore.ksessions.SessionManager;
import skuc.io.skuciocore.models.reports.AtSomeTimeReportFilters;
import skuc.io.skuciocore.models.reports.CollectReport;
import skuc.io.skuciocore.models.reports.MaxPeriodReportFilters;
import skuc.io.skuciocore.models.reports.ReportFilters;
import skuc.io.skuciocore.models.reports.ReportResult;

@RestController
@RequestMapping("reports")
public class ReportsController {
    
  private final SessionManager _sessionManager;

  @Autowired
  public ReportsController(
    SessionManager sessionManager
  ) {
    _sessionManager = sessionManager;
  }

  @GetMapping("normal")
  public ResponseEntity<ReportResult> getNormalReport(@RequestBody ReportFilters reportFilters) {

    var reportSession = _sessionManager.getReportSession();
    // reportSession.insert(reportFilters);
    reportSession.fireAllRules();

    reportSession.insert(new CollectReport());
    reportSession.fireAllRules();

    var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
    reportSession.dispose();

    return ResponseEntity.ok(reportResult);
  }

  @GetMapping("at-some-time")
  public ResponseEntity<ReportResult> getAtsomeTimeReport(@RequestBody AtSomeTimeReportFilters reportFilters) {

    var reportSession = _sessionManager.getReportSession();
    // reportSession.insert(reportFilters);
    reportSession.fireAllRules();

    reportSession.insert(new CollectReport());
    reportSession.fireAllRules();

    var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
    reportSession.dispose();

    return ResponseEntity.ok(reportResult);
  }

  @GetMapping("max-period")
  public ResponseEntity<ReportResult> getMaxPeriodReport(@RequestBody MaxPeriodReportFilters reportFilters) {

    var reportSession = _sessionManager.getReportSession();
    // reportSession.insert(reportFilters);
    reportSession.fireAllRules();

    reportSession.insert(new CollectReport());
    reportSession.fireAllRules();

    var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
    reportSession.dispose();

    return ResponseEntity.ok(reportResult);
  }

}
