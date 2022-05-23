package skuc.io.skuciocore.models.csm.configuration;

import java.util.HashMap;

import skuc.io.skuciocore.models.csm.BaseCsm;

public class Configuration extends BaseCsm {
  private HashMap<String, ThresholdsConfig> thresholdConfiguration = new HashMap<String, ThresholdsConfig>();
  private HashMap<String, StatusConfig> tatusConfiguration = new HashMap<String, StatusConfig>();

  public Configuration() {
  }

  public Configuration(HashMap<String, ThresholdsConfig> thresholdConfiguration,
      HashMap<String, StatusConfig> tatusConfiguration) {
    this.thresholdConfiguration = thresholdConfiguration;
    this.tatusConfiguration = tatusConfiguration;
  }

  public HashMap<String, ThresholdsConfig> getThresholdConfiguration() {
    return this.thresholdConfiguration;
  }

  public void setThresholdConfiguration(HashMap<String, ThresholdsConfig> thresholdConfiguration) {
    this.thresholdConfiguration = thresholdConfiguration;
  }

  public HashMap<String, StatusConfig> getTatusConfiguration() {
    return this.tatusConfiguration;
  }

  public void setTatusConfiguration(HashMap<String, StatusConfig> tatusConfiguration) {
    this.tatusConfiguration = tatusConfiguration;
  }

}
