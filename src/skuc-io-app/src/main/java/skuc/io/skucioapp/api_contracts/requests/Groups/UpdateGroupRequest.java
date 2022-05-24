package skuc.io.skucioapp.api_contracts.requests.Groups;

public class UpdateGroupRequest {
  private String name;

  public UpdateGroupRequest() {
  }

  public UpdateGroupRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
