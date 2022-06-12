package skuc.io.skuciocore.models.reports;

import java.util.List;

import skuc.io.skuciocore.models.csm.BaseCsm;

public class PredefinedReport extends BaseCsm {
  private String name;
  private String type;
  private int resolution;
  private List<ParamFilter> paramFilters;
  private String groupId;
  private String locationId;

  public PredefinedReport() {
    super();
  }

  public PredefinedReport(String name, String type, int resolution,
      List<ParamFilter> paramFilters, String groupId, String locationId) {
    this.name = name;
    this.type = type;
    this.resolution = resolution;
    this.paramFilters = paramFilters;
    this.groupId = groupId;
    this.locationId = locationId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getResolution() {
    return this.resolution;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

  public List<ParamFilter> getParamFilters() {
    return this.paramFilters;
  }

  public void setParamFilters(List<ParamFilter> paramFilters) {
    this.paramFilters = paramFilters;
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

}
