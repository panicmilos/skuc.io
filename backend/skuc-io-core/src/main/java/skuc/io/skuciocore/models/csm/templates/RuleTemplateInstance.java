package skuc.io.skuciocore.models.csm.templates;

import java.util.ArrayList;

import skuc.io.skuciocore.models.csm.BaseCsm;

public class RuleTemplateInstance extends BaseCsm {

  private String groupId;
  private String locationId;
  private String templateId;
  private ArrayList<Object> values;

  public RuleTemplateInstance() {
  }

  public RuleTemplateInstance(String groupId, String locationId, String templateId, ArrayList<Object> values) {
    this.groupId = groupId;
    this.locationId = locationId;
    this.templateId = templateId;
    this.values = values;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getLocationId() {
    return this.locationId;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  public String getTemplateId() {
    return this.templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }

  public ArrayList<Object> getValues() {
    return this.values;
  }

  public void setValues(ArrayList<Object> values) {
    this.values = values;
  }

}
