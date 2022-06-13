package skuc.io.skuciocore.models.reports;

public class PredefinedReportResult {
  private ReportResult report;
  private String reportType;
  private String name;

  public PredefinedReportResult() {
    super();
  }

  public PredefinedReportResult(ReportResult report, String reportType, String name) {
    this.report = report;
    this.reportType = reportType;
    this.name = name;
  }

  public ReportResult getReport() {
    return this.report;
  }

  public void setReport(ReportResult report) {
    this.report = report;
  }

  public String getReportType() {
    return this.reportType;
  }

  public void setReportType(String reportType) {
    this.reportType = reportType;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
