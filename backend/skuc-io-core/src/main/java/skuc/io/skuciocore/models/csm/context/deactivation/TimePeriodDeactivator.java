package skuc.io.skuciocore.models.csm.context.deactivation;

import java.util.Date;

import org.drools.core.time.impl.CronExpression;

import skuc.io.skuciocore.models.csm.context.BaseContextCsm;

public class TimePeriodDeactivator extends BaseContextCsm {
  private String cronStart;
  private String cronEnd;

  public TimePeriodDeactivator() {
  }

  public TimePeriodDeactivator(String cronStart, String cronEnd) {
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

  public boolean matches() {
    var date = new Date();
    date.setTime(date.getTime() - 1000);

    try {
      var next = new CronExpression(cronEnd).getNextValidTimeAfter(date);

      return next.toLocaleString().equals(new Date().toLocaleString());
    } catch (Exception e) {
      return false;
    }

  }

}
