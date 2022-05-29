package skuc.io.skucioapp.api_contracts.requests.Deactivators;


public class UpdateTimePeriodDeactivatorRequest {
  private String cronStart;
  private String cronEnd;

  public UpdateTimePeriodDeactivatorRequest() {
  }

  public UpdateTimePeriodDeactivatorRequest(String cronStart, String cronEnd) {
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
