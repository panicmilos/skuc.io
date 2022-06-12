package skuc.io.operators;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.drools.core.base.ValueType;
import org.drools.core.base.evaluators.EvaluatorDefinition;
import org.drools.core.base.evaluators.Operator;
import org.drools.core.spi.Evaluator;

public class IsInPeriodEvaluatorDefinition implements EvaluatorDefinition {
  protected static final String isInPeriodOp = "isInPeriod";

  public static Operator IS_IN_PERIOD;
  public static Operator NOT_IS_IN_PERIOD;

  private static String[] SUPPORTED_IDS;

  private IsInPeriodEvaluator evaluator;
  private IsInPeriodEvaluator negatedEvaluator;

  static {
    init();
  }

  static void init() {
    if (SUPPORTED_IDS == null) {
      IS_IN_PERIOD = Operator.addOperatorToRegistry(isInPeriodOp, false);
      NOT_IS_IN_PERIOD = Operator.addOperatorToRegistry(isInPeriodOp, true);
      SUPPORTED_IDS = new String[]{isInPeriodOp};
    }
  }

  @Override
  public String[] getEvaluatorIds() {
    return new String[]{isInPeriodOp};
  }

  @Override
  public boolean isNegatable() {
    return true;
  }

  @Override
  public Evaluator getEvaluator(ValueType type, Operator operator) {
    return this.getEvaluator(type, operator.getOperatorString(), operator.isNegated(), null);
  }

  @Override
  public Evaluator getEvaluator(ValueType type, Operator operator, String parameterText) {
    return this.getEvaluator(type, operator.getOperatorString(), operator.isNegated(), parameterText);
  }

  @Override
  public Evaluator getEvaluator(ValueType type, String operatorId, boolean isNegated, String parameterText) {
    return getEvaluator(type, operatorId, isNegated, parameterText, Target.BOTH, Target.BOTH);
  }

  @Override
  public Evaluator getEvaluator(ValueType type, String operatorId, boolean isNegated, String parameterText, Target leftTarget, Target rightTarget) {
    return isNegated ? negatedEvaluator == null ? new IsInPeriodEvaluator(type, isNegated) : negatedEvaluator : evaluator == null ? new IsInPeriodEvaluator(type, isNegated) : evaluator;
  }

  @Override
  public boolean supportsType(ValueType type) {
    return true;
  }

  @Override
  public Target getTarget() {
    return Target.BOTH;
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeObject(evaluator);
    out.writeObject(negatedEvaluator);
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    evaluator = (IsInPeriodEvaluator) in.readObject();
    negatedEvaluator = (IsInPeriodEvaluator) in.readObject();
  }

}
