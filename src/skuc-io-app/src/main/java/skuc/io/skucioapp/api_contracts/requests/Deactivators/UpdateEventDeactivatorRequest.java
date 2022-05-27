package skuc.io.skucioapp.api_contracts.requests.Deactivators;

public class UpdateEventDeactivatorRequest {
  private String eventType;

  public UpdateEventDeactivatorRequest() {
  }

  public UpdateEventDeactivatorRequest(String eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return this.eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

}
