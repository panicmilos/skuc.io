package skuc.io.skuciocore.models.events.device;

public class Aggregate {
  private Double min;
  private Double max;
  private Double sum;
  private Double average;
  private Long count;

  public Aggregate() {
  }

  public Aggregate(Double min, Double max, Double sum, Double average, Long count) {
    this.min = min;
    this.max = max;
    this.sum = sum;
    this.average = average;
    this.count = count;
  }

  public Double getMin() {
    return this.min;
  }

  public void setMin(Double min) {
    this.min = min;
  }

  public Double getMax() {
    return this.max;
  }

  public void setMax(Double max) {
    this.max = max;
  }

  public Double getSum() {
    return this.sum;
  }

  public void setSum(Double sum) {
    this.sum = sum;
  }

  public Double getAverage() {
    return this.average;
  }

  public void setAverage(Double average) {
    this.average = average;
  }

  public Long getCount() {
    return this.count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

}
