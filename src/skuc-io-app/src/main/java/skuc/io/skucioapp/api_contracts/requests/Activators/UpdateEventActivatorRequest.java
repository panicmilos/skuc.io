package skuc.io.skucioapp.api_contracts.requests.Activators;

public class UpdateEventActivatorRequest {
  private String eventType;

  public UpdateEventActivatorRequest() {
  }

  public UpdateEventActivatorRequest(String eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return this.eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

}
