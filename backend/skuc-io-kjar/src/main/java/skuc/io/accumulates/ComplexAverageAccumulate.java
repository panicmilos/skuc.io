package skuc.io.accumulates;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.kie.api.runtime.rule.AccumulateFunction;

import skuc.io.skuciocore.models.events.device.ValueAggregated;

public class ComplexAverageAccumulate implements AccumulateFunction {

  public static class ComplexAverageContext implements Externalizable {
    public Double sumValue = 0D;
    public Long countValue = 0L;

    public ComplexAverageContext() {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      sumValue = in.readDouble();
      countValue = in.readLong();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
      out.writeDouble(sumValue);
      out.writeLong(countValue);
    }
  }

  @Override
  public Serializable createContext() {
    return new ComplexAverageContext();
  }

  @Override
  public void init(Serializable context) throws Exception {

  }

  @Override
  public void accumulate(Serializable context, Object value) {
    ComplexAverageContext c = (ComplexAverageContext) context;

    ValueAggregated valueAggregated = (ValueAggregated) value;
    c.sumValue += valueAggregated.getAggregate().getSum();
    c.countValue += valueAggregated.getAggregate().getCount();
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
    ComplexAverageContext c = (ComplexAverageContext) context;

    return c.sumValue / c.countValue;
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
