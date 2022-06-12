package skuc.io.operators;

import org.drools.core.base.BaseEvaluator;
import org.drools.core.base.ValueType;
import org.drools.core.common.InternalFactHandle;
import org.drools.core.common.InternalWorkingMemory;
import org.drools.core.rule.VariableRestriction;
import org.drools.core.rule.VariableRestriction.ObjectVariableContextEntry;
import org.drools.core.spi.FieldValue;
import org.drools.core.spi.InternalReadAccessor;

import skuc.io.skuciocore.models.events.device.Aggregate;
import skuc.io.skuciocore.models.events.device.ValueAggregated;
import skuc.io.skuciocore.models.reports.ParamFilter;
import skuc.io.skuciocore.models.reports.ValueFilter;

public class SatisfiesFiltersEvaluator extends BaseEvaluator {
  private final boolean isNegated;

  public SatisfiesFiltersEvaluator(ValueType type, boolean isNegated) {
    super(type, isNegated ? SatisfiesFiltersEvaluatorDefinition.NOT_SATISFIES_FILTERS : SatisfiesFiltersEvaluatorDefinition.SATISFIES_FILTERS);
    this.isNegated = isNegated;
  }
  
  @Override
  public boolean evaluate(InternalWorkingMemory workingMemory, InternalReadAccessor extractor, InternalFactHandle factHandle, FieldValue value) {
    Object valueAggregated = extractor.getValue(workingMemory, factHandle.getObject());
    
    return this.isNegated ^ this.evaluateUnsafe(valueAggregated, value.getValue());
  }

  @Override
  public boolean evaluate(InternalWorkingMemory workingMemory, InternalReadAccessor leftExtractor, InternalFactHandle left, InternalReadAccessor rightExtractor, InternalFactHandle right) {
    Object valueAggregated = leftExtractor.getValue(workingMemory, left.getObject());
    Object paramFilter = rightExtractor.getValue(workingMemory, right.getObject());
      
    return this.isNegated ^ this.evaluateUnsafe(valueAggregated, paramFilter);
  }

  @Override
  public boolean evaluateCachedLeft(InternalWorkingMemory workingMemory, VariableRestriction.VariableContextEntry context, InternalFactHandle right) {
    Object valueAggregated = context.getFieldExtractor().getValue(workingMemory, right.getObject());
    Object paramFilter = ((ObjectVariableContextEntry)context).left;
    
    return this.isNegated ^ this.evaluateUnsafe(valueAggregated, paramFilter);
  }

  @Override
  public boolean evaluateCachedRight(InternalWorkingMemory workingMemory, VariableRestriction.VariableContextEntry context, InternalFactHandle left) {
    Object valueAggregated = ((ObjectVariableContextEntry)context).right;
    Object paramFilter = context.getFieldExtractor().getValue(workingMemory, left.getObject());
    
    return this.isNegated ^ this.evaluateUnsafe(valueAggregated, paramFilter);
  }
  
  private boolean evaluateUnsafe(Object valueAggregated, Object paramFilter) {
    //if the object is not an Order return false.
    if (!(valueAggregated instanceof ValueAggregated)) {
      throw new IllegalArgumentException(valueAggregated.getClass() + " can't be casted to type ValueAggregated");
    }

    if (!(paramFilter instanceof ParamFilter)) {
      throw new IllegalArgumentException(paramFilter.getClass() + " can't be casted to type ParamFilter");
    }
    
    return this.evaluate((ValueAggregated)valueAggregated, (ParamFilter)paramFilter);
  }
  
  private boolean evaluate(ValueAggregated valueAggregated, ParamFilter paramFilter) {
    System.out.println("ID: " + valueAggregated.getId() +  " PARENT ID: " + valueAggregated.getParentId()  + " ALGORITAM: " + paramFilter.getAlgorithm());
    System.console().flush();
    if (!valueAggregated.getParamName().equals(paramFilter.getParamName())) {
      return false;
    }

    for(ValueFilter valueFilter : paramFilter.getValueFilters()) {
      if (!evaluateValueFilter(valueAggregated.getAggregate(), valueFilter)) {
        return false;
      }
    }

    System.out.println("=====================");
    System.out.println("ID: " + valueAggregated.getId() +  " PARENT ID: " + valueAggregated.getParentId()  + " ALGORITAM: " + paramFilter.getAlgorithm());
    System.out.println(" Value: " + valueAggregated.getAggregate().getValue(paramFilter.getAlgorithm()));
    for(ValueFilter valueFilter : paramFilter.getValueFilters()) {
      System.out.print("Comparator: " + valueFilter.getComparator());
      System.out.println(" Value: " + valueFilter.getValue());
    }
    System.console().flush();


    return true;
  }

  private boolean evaluateValueFilter(Aggregate aggregate, ValueFilter filter) {
    if (filter.getAlgorithm().equals("count")) {
      return evaluateLongCompareValue((Long)aggregate.getValue("count"), filter.getComparator(), (Long)filter.getValue());
    }

    return evaluateDoubleCompareValue((Double)aggregate.getValue(filter.getAlgorithm()), filter.getComparator(), (Double)filter.getValue());
  }

  private boolean evaluateLongCompareValue(Long value, String comparer, Long threshold) {
    switch(comparer) {
      case "=": return value.longValue() == threshold.longValue();
      case "!=": return value.longValue() != threshold.longValue();
      case ">": return value.longValue() > threshold.longValue();
      case ">=": return value.longValue() >= threshold.longValue();
      case "<": return value.longValue() < threshold.longValue();
      case "<=": return value.longValue() <= threshold.longValue();
    }

    return false;
  }

  private boolean evaluateDoubleCompareValue(Double value, String comparer, Double threshold) {
    switch(comparer) {
      case "=": return value.doubleValue() == threshold.doubleValue();
      case "!=": return value.doubleValue() != threshold.doubleValue();
      case ">": return value.doubleValue() > threshold.doubleValue();
      case ">=": return value.doubleValue() >= threshold.doubleValue();
      case "<": return value.doubleValue() < threshold.doubleValue();
      case "<=": return value.doubleValue() <= threshold.doubleValue();
    }

    return false;
  }

}
