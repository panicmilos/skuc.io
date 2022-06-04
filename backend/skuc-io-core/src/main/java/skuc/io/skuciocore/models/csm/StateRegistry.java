package skuc.io.skuciocore.models.csm;

import java.util.HashMap;

public class StateRegistry extends BaseCsm {
  private String objectId;
  private HashMap<String, String> registry = new HashMap<String, String>();

  public StateRegistry() {
  }

  public StateRegistry(
      String objectId,
      HashMap<String, String> registry) {
    this.registry = registry;
    this.objectId = objectId;
  }

  public HashMap<String, String> getRegistry() {
    return this.registry;
  }

  public void setRegistry(HashMap<String, String> registry) {
    this.registry = registry;
  }

  public void setState(String key, String value) {
    this.registry.put(key, value);
  }

  public String getState(String key) {
    return this.registry.get(key);
  }

  public boolean isState(String key, String value) {
    if(!this.registry.containsKey(key)) return false;
    return this.registry.get(key).equals(value);
  }

  public String getObjectId() {
    return this.objectId;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

}
