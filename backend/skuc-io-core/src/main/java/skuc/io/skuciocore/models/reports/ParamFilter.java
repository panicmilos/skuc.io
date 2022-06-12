package skuc.io.skuciocore.models.reports;

import java.util.List;

public class ParamFilter {
  private String paramName;
  private String algorithm;
  private List<ValueFilter> valueFilters;

  public ParamFilter() {
    super();
  }

  public ParamFilter(String paramName, String algorithm, List<ValueFilter> valueFilters) {
    this.paramName = paramName;
    this.algorithm = algorithm;
    this.valueFilters = valueFilters;
  }

  public String getParamName() {
    return this.paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public String getAlgorithm() {
    return this.algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  public void setValueFilters(List<ValueFilter> valueFilters) {
    this.valueFilters = valueFilters;
  }

  public List<ValueFilter> getValueFilters() {
    return this.valueFilters;
  }

  public void setValueFilter(List<ValueFilter> valueFilters) {
    this.valueFilters = valueFilters;
  }

}
