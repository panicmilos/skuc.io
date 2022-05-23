package skuc.io.skuciocore.models.csm.configuration;

public class StatusConfig {
  private DeviceStatus expectedValue;

  public StatusConfig() {
  }

  public StatusConfig(DeviceStatus expectedValue) {
    this.expectedValue = expectedValue;
  }

  public DeviceStatus getExpectedValue() {
    return this.expectedValue;
  }

  public void setExpectedValue(DeviceStatus expectedValue) {
    this.expectedValue = expectedValue;
  }

}