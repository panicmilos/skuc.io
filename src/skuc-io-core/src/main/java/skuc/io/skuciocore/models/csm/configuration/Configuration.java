package skuc.io.skuciocore.models.csm.configuration;

import java.util.HashMap;

public class Configuration {
  private HashMap<String, ThresholdsConfig> thresholdConfiguration = new HashMap<String, ThresholdsConfig>();
  private HashMap<String, StatusConfig> statusConfiguration = new HashMap<String, StatusConfig>();

  public Configuration() {
  }

  public Configuration(
    HashMap<String, ThresholdsConfig> thresholdConfiguration,
    HashMap<String, StatusConfig> statusConfiguration
  ) {
    this.thresholdConfiguration = thresholdConfiguration;
    this.statusConfiguration = statusConfiguration;
  }

  public HashMap<String, ThresholdsConfig> getThresholdConfiguration() {
    return this.thresholdConfiguration;
  }

  public void setThresholdConfiguration(HashMap<String, ThresholdsConfig> thresholdConfiguration) {
    this.thresholdConfiguration = thresholdConfiguration;
  }

  public HashMap<String, StatusConfig> getStatusConfiguration() {
    return this.statusConfiguration;
  }

  public void setStatusConfiguration(HashMap<String, StatusConfig> statusConfiguration) {
    this.statusConfiguration = statusConfiguration;
  }

}
