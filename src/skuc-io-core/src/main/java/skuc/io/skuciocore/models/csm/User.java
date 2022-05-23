package skuc.io.skuciocore.models.csm;

public class User extends BaseCsm {
  private String password;
  private String fullName;
  private String Address;
  private String PhoneNumber;

  public User() {
  }

  public User(String password, String fullName, String Address, String PhoneNumber) {
    this.password = password;
    this.fullName = fullName;
    this.Address = Address;
    this.PhoneNumber = PhoneNumber;
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
    return this.Address;
  }

  public void setAddress(String Address) {
    this.Address = Address;
  }

  public String getPhoneNumber() {
    return this.PhoneNumber;
  }

  public void setPhoneNumber(String PhoneNumber) {
    this.PhoneNumber = PhoneNumber;
  }
}
