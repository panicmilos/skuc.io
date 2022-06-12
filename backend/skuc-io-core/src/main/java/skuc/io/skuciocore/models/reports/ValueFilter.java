package skuc.io.skuciocore.models.reports;

public class ValueFilter {
  private String comparator;
  private String algorithm;
  private Object value;

  public ValueFilter() {
    super();
  }

  public ValueFilter(String comparator, String algorithm, Object value) {
    this.comparator = comparator;
    this.algorithm = algorithm;
    this.value = value;
  }

  public String getComparator() {
    return this.comparator;
  }

  public void setComparator(String comparator) {
    this.comparator = comparator;
  }

  public String getAlgorithm() {
    return this.algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  public Object getValue() {
    return this.value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

}
