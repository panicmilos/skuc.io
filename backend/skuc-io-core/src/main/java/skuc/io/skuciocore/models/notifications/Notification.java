package skuc.io.skuciocore.models.notifications;

public abstract class Notification {
  private String type;
  private String groupId;

  public Notification() {
  }

  public Notification(String type, String groupId) {
    this.type = type;
    this.groupId = groupId;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

}
