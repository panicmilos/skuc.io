package skuc.io.skuciocore.models.csm;

public class Location extends BaseCsm {
  private String name;
  private String groupId;

  public Location() {
  }

  public Location(String name, String groupId) {
    this.name = name;
    this.groupId = groupId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

}