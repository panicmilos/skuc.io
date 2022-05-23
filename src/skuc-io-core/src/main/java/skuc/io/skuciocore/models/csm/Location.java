package skuc.io.skuciocore.models.csm;

import java.util.UUID;

public class Location extends BaseCsm {
  private String name;
  private UUID userId;

  public Location() {
  }

  public Location(String name, UUID userId) {
    this.name = name;
    this.userId = userId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getUserId() {
    return this.userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

}