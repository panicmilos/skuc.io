package skuc.io.skucioapp.api_contracts.requests.Users;

public class ChangePasswordRequest {
  private String currentPassword;
  private String newPassword;

  public ChangePasswordRequest() {
  }

  public ChangePasswordRequest(String currentPassword, String newPassword) {
    this.currentPassword = currentPassword;
    this.newPassword = newPassword;
  }

  public String getCurrentPassword() {
    return this.currentPassword;
  }

  public void setCurrentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
  }

  public String getNewPassword() {
    return this.newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

}
