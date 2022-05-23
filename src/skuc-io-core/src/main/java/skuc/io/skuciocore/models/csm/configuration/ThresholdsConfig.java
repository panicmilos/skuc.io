package skuc.io.skuciocore.models.csm.configuration;

public class ThresholdsConfig {
  private float min;
  private float max;

  public ThresholdsConfig() {
  }

  public ThresholdsConfig(float min, float max) {
    this.min = min;
    this.max = max;
  }

  public float getMin() {
    return this.min;
  }

  public void setMin(float min) {
    this.min = min;
  }

  public float getMax() {
    return this.max;
  }

  public void setMax(float max) {
    this.max = max;
  }

}