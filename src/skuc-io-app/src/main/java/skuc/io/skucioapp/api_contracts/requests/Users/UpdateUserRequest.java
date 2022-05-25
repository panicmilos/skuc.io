package skuc.io.skucioapp.api_contracts.requests.Users;

public class UpdateUserRequest {
  private String fullName;
  private String address;
  private String phoneNumber;

  public UpdateUserRequest() {
  }

  public UpdateUserRequest(String fullName, String address, String phoneNumber) {
    this.fullName = fullName;
    this.address = address;
    this.phoneNumber = phoneNumber;
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

}
