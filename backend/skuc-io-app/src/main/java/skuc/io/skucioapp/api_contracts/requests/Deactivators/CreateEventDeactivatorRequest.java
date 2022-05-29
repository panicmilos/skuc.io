package skuc.io.skucioapp.api_contracts.requests.Deactivators;


public class CreateEventDeactivatorRequest {
  private String eventType;

  public CreateEventDeactivatorRequest() {
  }

  public CreateEventDeactivatorRequest(String eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return this.eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }
  
}
