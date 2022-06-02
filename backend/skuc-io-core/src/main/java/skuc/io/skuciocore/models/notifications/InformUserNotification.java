package skuc.io.skuciocore.models.notifications;

public class InformUserNotification extends Notification {
  private String message;

  public InformUserNotification() {
  }

  public InformUserNotification(String message, String groupId) {
    super("InformUser", groupId);
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
