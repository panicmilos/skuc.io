package skuc.io.skuciocore.models.events.device;

public class Aggregate {
  private float min;
  private float max;
  private float sum;
  private float average;
  private float count;

  public Aggregate() {
  }

  public Aggregate(float min, float max, float sum, float average, float count) {
    this.min = min;
    this.max = max;
    this.sum = sum;
    this.average = average;
    this.count = count;
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

  public float getSum() {
    return this.sum;
  }

  public void setSum(float sum) {
    this.sum = sum;
  }

  public float getAverage() {
    return this.average;
  }

  public void setAverage(float average) {
    this.average = average;
  }

  public float getCount() {
    return this.count;
  }

  public void setCount(float count) {
    this.count = count;
  }

}
