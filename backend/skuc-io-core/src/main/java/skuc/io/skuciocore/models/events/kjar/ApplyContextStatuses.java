package skuc.io.skuciocore.models.events.kjar;

public class ApplyContextStatuses {
    private String contextId;

    public ApplyContextStatuses() {
    }

    public ApplyContextStatuses(String contextId) {
        this.contextId = contextId;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }
}