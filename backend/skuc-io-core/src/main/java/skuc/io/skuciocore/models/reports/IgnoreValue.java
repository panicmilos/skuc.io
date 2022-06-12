package skuc.io.skuciocore.models.reports;

import skuc.io.skuciocore.models.events.device.ValueAggregated;

public class IgnoreValue {
  public ValueAggregated valueAggregated;

  public IgnoreValue() {
    super();
  }

  public IgnoreValue(ValueAggregated valueAggregated) {
    this.valueAggregated = valueAggregated;
  }

  public ValueAggregated getValueAggregated() {
    return this.valueAggregated;
  }

  public void setValueAggregated(ValueAggregated valueAggregated) {
    this.valueAggregated = valueAggregated;
  }

}
