package skuc.io.skucioapp.api_contracts.requests.Groups;

public class CreateGroupRequest {
  private String name;

  public CreateGroupRequest() {
  }

  public CreateGroupRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
