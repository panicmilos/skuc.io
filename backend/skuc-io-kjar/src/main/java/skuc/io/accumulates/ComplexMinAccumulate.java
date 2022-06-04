package skuc.io.accumulates;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.kie.api.runtime.rule.AccumulateFunction;

import skuc.io.skuciocore.models.events.device.ValueAggregated;

public class ComplexMinAccumulate implements AccumulateFunction {

  public static class ComplexMinContext implements Externalizable {
    public ValueAggregated minValueAggregated = null;
    public Double minValue = Double.MAX_VALUE;

    public ComplexMinContext() {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      minValueAggregated = (ValueAggregated) in.readObject();
      minValue = in.readDouble();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(minValueAggregated);
      out.writeDouble(minValue);
    }
  }

  @Override
  public Serializable createContext() {
    return new ComplexMinContext();
  }

  @Override
  public void init(Serializable context) throws Exception {

  }

  @Override
  public void accumulate(Serializable context, Object value) {
    ComplexMinContext c = (ComplexMinContext) context;

    ValueAggregated valueAggregated = (ValueAggregated) value;
    if (valueAggregated.getAggregate().getMin() < c.minValue) {
      c.minValue = valueAggregated.getAggregate().getMin();
      c.minValueAggregated = valueAggregated;
    }
  }

  @Override
  public boolean supportsReverse() {
    return false;
  }

  @Override
  public void reverse(Serializable context, Object value) throws Exception {
  }

  @Override
  public Object getResult(Serializable context) throws Exception {
    return ((ComplexMinContext) context).minValue;
  }

  @Override
  public Class<?> getResultType() {
    return Double.class;
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {

  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

  }

}
