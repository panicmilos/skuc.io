package skuc.io.skucioapp.api_contracts.requests.PredefinedReports;

import java.util.List;

import skuc.io.skuciocore.models.reports.ParamFilter;

public class UpdatePredefinedReportRequest {
  private String name;
  private String type;
  private int resolution;
  private List<ParamFilter> paramFilters;

  public UpdatePredefinedReportRequest() {
  }

  public UpdatePredefinedReportRequest(String name, String type, int resolution, List<ParamFilter> paramFilters) {
    this.name = name;
    this.type = type;
    this.resolution = resolution;
    this.paramFilters = paramFilters;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getResolution() {
    return this.resolution;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

  public List<ParamFilter> getParamFilters() {
    return this.paramFilters;
  }

  public void setParamFilters(List<ParamFilter> paramFilters) {
    this.paramFilters = paramFilters;
  }

}
