package skuc.io.skucioapp.api_contracts.requests.Users;

public class CreateUserRequest {
  private String email;
  private String password;
  private String fullName;
  private String address;
  private String phoneNumber;
  private String role;

  public CreateUserRequest() {
  }

  public CreateUserRequest(String email, String password, String fullName, String address, String phoneNumber, String role) {
    this.email = email;
    this.password = password;
    this.fullName = fullName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.role = role;
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

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
