package skuc.io.skuciocore.models.csm.configuration;

public class StatusConfig {
  private String expectedValue;

  public StatusConfig() {
  }

  public StatusConfig(String expectedValue) {
    this.expectedValue = expectedValue;
  }

  public String getExpectedValue() {
    return this.expectedValue;
  }

  public void setExpectedValue(String expectedValue) {
    this.expectedValue = expectedValue;
  }

}