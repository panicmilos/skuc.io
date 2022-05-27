package skuc.io.skuciocore.models.csm.context;

import skuc.io.skuciocore.models.csm.BaseCsm;

public class BaseContextCsm extends BaseCsm {
  private String contextId;

  public BaseContextCsm() {
  }

  public BaseContextCsm(String contextId) {
    this.contextId = contextId;
  }

  public String getContextId() {
    return this.contextId;
  }

  public void setContextId(String contextId) {
    this.contextId = contextId;
  }

}
