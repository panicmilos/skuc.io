package skuc.io.skuciocore.models.reports;

import java.util.List;

public class AtSomeTimeReportFilters {
  private int resolution;
  private LocalDateTimePeriod period;
  private List<ParamFilter> paramFilters;

  public AtSomeTimeReportFilters() {
    super();
  }

  public AtSomeTimeReportFilters(int resolution, LocalDateTimePeriod period, List<ParamFilter> paramFilters) {
    this.resolution = resolution;
    this.period = period;
    this.paramFilters = paramFilters;
  }

  public int getResolution() {
    return this.resolution;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

  public LocalDateTimePeriod getPeriod() {
    return this.period;
  }

  public void setPeriod(LocalDateTimePeriod period) {
    this.period = period;
  }

  public List<ParamFilter> getParamFilters() {
    return this.paramFilters;
  }

  public void setParamFilters(List<ParamFilter> paramFilters) {
    this.paramFilters = paramFilters;
  }

}
