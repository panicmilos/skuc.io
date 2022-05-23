package skuc.io.skuciocore.models.csm.context;

import java.util.UUID;

import skuc.io.skuciocore.models.csm.BaseCsm;

public class BaseContextCsm extends BaseCsm {
  private UUID contextId;

  public BaseContextCsm() {
  }

  public BaseContextCsm(UUID contextId) {
    this.contextId = contextId;
  }

  public UUID getContextId() {
    return this.contextId;
  }

  public void setContextId(UUID contextId) {
    this.contextId = contextId;
  }

}
