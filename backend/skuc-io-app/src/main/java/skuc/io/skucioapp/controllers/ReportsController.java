package skuc.io.skucioapp.controllers;

import org.drools.core.ClassObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skuciocore.ksessions.SessionManager;
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

  @PostMapping("normal")
  public ResponseEntity<ReportResult> getNormalReport(@RequestBody ReportFilters reportFilters) {

    var reportSession = _sessionManager.getReportSession();
    reportSession.insert(reportFilters);
    reportSession.fireAllRules();

    var reportResult = (ReportResult)reportSession.getObjects(new ClassObjectFilter(ReportResult.class)).iterator().next();
    reportSession.dispose();

    return ResponseEntity.ok(reportResult);
  }

}
