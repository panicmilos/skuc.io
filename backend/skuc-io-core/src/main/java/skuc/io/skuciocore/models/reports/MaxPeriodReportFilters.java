package skuc.io.skuciocore.models.reports;

import java.util.List;

public class MaxPeriodReportFilters {
  private LocalDateTimePeriod period;
  private List<ParamFilter> paramFilters;

  public MaxPeriodReportFilters() {
    super();
  }

  public MaxPeriodReportFilters(LocalDateTimePeriod period, List<ParamFilter> paramFilters) {
    this.period = period;
    this.paramFilters = paramFilters;
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
