package skuc.io.skucioapp.api_contracts.requests.Activators;


public class CreateEventActivatorRequest {
  private String eventType;

  public CreateEventActivatorRequest() {
  }

  public CreateEventActivatorRequest(String eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return this.eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }
  
}
