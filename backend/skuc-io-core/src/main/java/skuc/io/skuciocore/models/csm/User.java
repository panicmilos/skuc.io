package skuc.io.skuciocore.models.csm;

import skuc.io.skuciocore.models.csm.enums.Role;

public class User extends BaseCsm {
  private String groupId;
  private String email;
  private String password;
  private String fullName;
  private String address;
  private String phoneNumber;
  private Role role;

  public User() {
  }

  public User(String groupId, String email, String password, String fullName, String address, String phoneNumber, Role role) {
    this.groupId = groupId;
    this.email = email;
    this.password = password;
    this.fullName = fullName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.role = role;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
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

  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
