package skuc.io.skuciocore.models.events.kjar;

import skuc.io.skuciocore.models.events.device.ValueReceived;

public class ValueReceivedCopy {
  private String paramName;
  private float value;
  private String deviceType;

  public ValueReceivedCopy() {

  }

  public ValueReceivedCopy(ValueReceived vr) {
    this.paramName = vr.getParamName();
    this.value = vr.getValue();
    this.deviceType = vr.getDeviceType();
  }

  public String getParamName() {
    return this.paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public float getValue() {
    return this.value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public String getDeviceType() {
    return this.deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

}
