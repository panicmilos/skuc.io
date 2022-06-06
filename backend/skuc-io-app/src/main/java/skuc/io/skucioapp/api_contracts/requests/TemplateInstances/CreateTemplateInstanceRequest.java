package skuc.io.skucioapp.api_contracts.requests.TemplateInstances;

import java.util.ArrayList;

public class CreateTemplateInstanceRequest {
  private String locationId;
  private ArrayList<Object> values;

  public CreateTemplateInstanceRequest() {
  }

  public CreateTemplateInstanceRequest(String locationId, ArrayList<Object> values) {
    this.locationId = locationId;
    this.values = values;
  }

  public String getLocationId() {
    return this.locationId;
  }

  public void setLocationId(String locationId) {
    this.locationId = locationId;
  }

  public ArrayList<Object> getValues() {
    return this.values;
  }

  public void setValues(ArrayList<Object> values) {
    this.values = values;
  }

}
