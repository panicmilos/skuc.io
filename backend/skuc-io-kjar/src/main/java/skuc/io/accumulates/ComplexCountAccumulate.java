package skuc.io.accumulates;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.kie.api.runtime.rule.AccumulateFunction;

import skuc.io.skuciocore.models.events.device.ValueAggregated;

public class ComplexCountAccumulate implements AccumulateFunction {

  public static class ComplexCountContext implements Externalizable {
    public Long countValue = 0L;

    public ComplexCountContext() {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      countValue = in.readLong();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
      out.writeLong(countValue);
    }
  }

  @Override
  public Serializable createContext() {
    return new ComplexCountContext();
  }

  @Override
  public void init(Serializable context) throws Exception {

  }

  @Override
  public void accumulate(Serializable context, Object value) {
    ComplexCountContext c = (ComplexCountContext) context;

    ValueAggregated valueAggregated = (ValueAggregated) value;
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
    return ((ComplexCountContext) context).countValue;
  }

  @Override
  public Class<?> getResultType() {
    return Long.class;
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {

  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

  }

}
