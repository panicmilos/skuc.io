package skuc.io.accumulates;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.kie.api.runtime.rule.AccumulateFunction;

import skuc.io.skuciocore.models.events.device.ValueAggregated;

public class ComplexMaxAccumulate implements AccumulateFunction {

  public static class ComplexMaxContext implements Externalizable {
    public ValueAggregated maxValueAggregated = null;
    public Double maxValue = Double.MIN_VALUE;

    public ComplexMaxContext() {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      maxValueAggregated = (ValueAggregated) in.readObject();
      maxValue = in.readDouble();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(maxValueAggregated);
      out.writeDouble(maxValue);
    }
  }

  @Override
  public Serializable createContext() {
    return new ComplexMaxContext();
  }

  @Override
  public void init(Serializable context) throws Exception {

  }

  @Override
  public void accumulate(Serializable context, Object value) {
    ComplexMaxContext c = (ComplexMaxContext) context;

    ValueAggregated valueAggregated = (ValueAggregated) value;
    if (valueAggregated.getAggregate().getMax() > c.maxValue) {
      c.maxValue = valueAggregated.getAggregate().getMax();
      c.maxValueAggregated = valueAggregated;
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
    return ((ComplexMaxContext) context).maxValue;
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
