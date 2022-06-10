package skuc.io.skuciocore.models.notifications;

public class InformUserNotification extends Notification {
  private String message;

  public InformUserNotification() {
  }

  public InformUserNotification(String message, String namespaceId) {
    super("InformUser", namespaceId);
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
