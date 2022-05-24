package skuc.io.skucioapp.api_contracts.requests;

public class CreateUserRequest {
  private String email;
  private String password;

  public CreateUserRequest() {
  }

  public CreateUserRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
