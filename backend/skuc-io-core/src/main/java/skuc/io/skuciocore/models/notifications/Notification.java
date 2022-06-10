package skuc.io.skuciocore.models.notifications;

public abstract class Notification {
  private String type;
  private String namespaceId;

  public Notification() {
  }

  public Notification(String type, String namespaceId) {
    this.type = type;
    this.namespaceId = namespaceId;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getNamespaceId() {
    return this.namespaceId;
  }

  public void setNamespaceId(String namespaceId) {
    this.namespaceId = namespaceId;
  }

}
