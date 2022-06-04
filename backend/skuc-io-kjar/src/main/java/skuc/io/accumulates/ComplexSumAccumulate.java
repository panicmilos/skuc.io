package skuc.io.accumulates;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.kie.api.runtime.rule.AccumulateFunction;

import skuc.io.skuciocore.models.events.device.ValueAggregated;

public class ComplexSumAccumulate implements AccumulateFunction {

  public static class ComplexSumContext implements Externalizable {
    public Double sumValue = 0D;

    public ComplexSumContext() {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      sumValue = in.readDouble();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
      out.writeDouble(sumValue);
    }
  }

  @Override
  public Serializable createContext() {
    return new ComplexSumContext();
  }

  @Override
  public void init(Serializable context) throws Exception {

  }

  @Override
  public void accumulate(Serializable context, Object value) {
    ComplexSumContext c = (ComplexSumContext) context;

    ValueAggregated valueAggregated = (ValueAggregated) value;
    c.sumValue += valueAggregated.getAggregate().getSum();
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
    return ((ComplexSumContext) context).sumValue;
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
