package skuc.io.skuciocore.models.csm.context.activation;

import skuc.io.skuciocore.models.csm.context.BaseContextCsm;

public class TimePeriodActivator extends BaseContextCsm {
  private String cronStart;
  private String cronEnd;

  public TimePeriodActivator() {
  }

  public TimePeriodActivator(String cronStart, String cronEnd) {
    this.cronStart = cronStart;
    this.cronEnd = cronEnd;
  }

  public String getCronStart() {
    return this.cronStart;
  }

  public void setCronStart(String cronStart) {
    this.cronStart = cronStart;
  }

  public String getCronEnd() {
    return this.cronEnd;
  }

  public void setCronEnd(String cronEnd) {
    this.cronEnd = cronEnd;
  }

}
