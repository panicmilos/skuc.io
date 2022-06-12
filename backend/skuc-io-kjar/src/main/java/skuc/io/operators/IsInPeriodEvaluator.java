package skuc.io.operators;

import java.time.LocalDateTime;

import org.drools.core.base.BaseEvaluator;
import org.drools.core.base.ValueType;
import org.drools.core.common.InternalFactHandle;
import org.drools.core.common.InternalWorkingMemory;
import org.drools.core.rule.VariableRestriction;
import org.drools.core.rule.VariableRestriction.ObjectVariableContextEntry;
import org.drools.core.spi.FieldValue;
import org.drools.core.spi.InternalReadAccessor;

import skuc.io.skuciocore.models.reports.LocalDateTimePeriod;


public class IsInPeriodEvaluator extends BaseEvaluator {
  private final boolean isNegated;

  public IsInPeriodEvaluator(ValueType type, boolean isNegated) {
    super(type, isNegated ? IsInPeriodEvaluatorDefinition.NOT_IS_IN_PERIOD : IsInPeriodEvaluatorDefinition.IS_IN_PERIOD);
    this.isNegated = isNegated;
  }
  
  @Override
  public boolean evaluate(InternalWorkingMemory workingMemory, InternalReadAccessor extractor, InternalFactHandle factHandle, FieldValue value) {
    Object createdAt = extractor.getValue(workingMemory, factHandle.getObject());
    
    return this.isNegated ^ this.evaluateUnsafe(createdAt, value.getValue());
  }

  @Override
  public boolean evaluate(InternalWorkingMemory workingMemory, InternalReadAccessor leftExtractor, InternalFactHandle left, InternalReadAccessor rightExtractor, InternalFactHandle right) {
    Object createdAt = leftExtractor.getValue(workingMemory, left.getObject());
    Object period = rightExtractor.getValue(workingMemory, right.getObject());
      
    return this.isNegated ^ this.evaluateUnsafe(createdAt, period);
  }

  @Override
  public boolean evaluateCachedLeft(InternalWorkingMemory workingMemory, VariableRestriction.VariableContextEntry context, InternalFactHandle right) {
    Object createdAt = context.getFieldExtractor().getValue(workingMemory, right.getObject());
    Object period = ((ObjectVariableContextEntry)context).left;
    
    return this.isNegated ^ this.evaluateUnsafe(createdAt, period);
  }

  @Override
  public boolean evaluateCachedRight(InternalWorkingMemory workingMemory, VariableRestriction.VariableContextEntry context, InternalFactHandle left) {
    Object createdAt = ((ObjectVariableContextEntry)context).right;
    Object period = context.getFieldExtractor().getValue(workingMemory, left.getObject());
    
    return this.isNegated ^ this.evaluateUnsafe(createdAt, period);
  }
  
  private boolean evaluateUnsafe(Object createdAt, Object period) {
    //if the object is not an Order return false.
    if (!(createdAt instanceof LocalDateTime)) {
      throw new IllegalArgumentException(createdAt.getClass() + " can't be casted to type LocalDateTime");
    }

    if (!(period instanceof LocalDateTimePeriod)) {
      throw new IllegalArgumentException(period.getClass() + " can't be casted to type LocalDateTimePeriod");
    }
    
    return this.evaluate((LocalDateTime)createdAt, (LocalDateTimePeriod)period);
  }
  
  private boolean evaluate(LocalDateTime createdAt, LocalDateTimePeriod period) {
    if(period.getFrom() != null && createdAt.isBefore(period.getFrom())) {
      return false;
    }
    
    if(period.getTo() != null && createdAt.isAfter(period.getTo())) {
      return false;
    }

    return true;
  }

}
