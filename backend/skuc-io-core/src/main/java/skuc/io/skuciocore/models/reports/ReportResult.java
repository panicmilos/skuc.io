package skuc.io.skuciocore.models.reports;

import java.util.List;

public class ReportResult {
  private List<ReportResultGroup> groups;

  public ReportResult() {
    super();
  }

  public ReportResult(List<ReportResultGroup> groups) {
    this.groups = groups;
  }

  public List<ReportResultGroup> getGroups() {
    return this.groups;
  }

  public void setGroups(List<ReportResultGroup> groups) {
    this.groups = groups;
  }

}
