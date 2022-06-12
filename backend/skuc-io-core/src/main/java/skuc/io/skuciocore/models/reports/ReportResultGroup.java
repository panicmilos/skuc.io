package skuc.io.skuciocore.models.reports;

import java.util.List;

public class ReportResultGroup {
  private String paramName;
  private String algorithm;
  private List<ReportResultValue> reportResultValues;

  public ReportResultGroup() {
    super();
  }

  public ReportResultGroup(String paramName, String algorithm, List<ReportResultValue> reportResultValues) {
    this.paramName = paramName;
    this.algorithm = algorithm;
    this.reportResultValues = reportResultValues;
  }

  public String getParamName() {
    return this.paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public List<ReportResultValue> getReportResultValues() {
    return this.reportResultValues;
  }

  public void setReportResultValues(List<ReportResultValue> reportResultValues) {
    this.reportResultValues = reportResultValues;
  }

  public String getAlgorithm() {
    return this.algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

}
