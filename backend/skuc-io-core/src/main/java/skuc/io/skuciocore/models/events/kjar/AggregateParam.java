package skuc.io.skuciocore.models.events.kjar;

public class AggregateParam {

  private String paramName;

  public AggregateParam() {
  }

  public AggregateParam(String paramName) {
    this.paramName = paramName;
  }

  public String getParamName() {
    return this.paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

}
