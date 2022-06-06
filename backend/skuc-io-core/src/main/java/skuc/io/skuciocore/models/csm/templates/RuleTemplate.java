package skuc.io.skuciocore.models.csm.templates;

import java.util.List;

import skuc.io.skuciocore.models.csm.BaseCsm;

public class RuleTemplate extends BaseCsm {
    private String groupId;
    private String name;
    private List<String> parameters;
    private String template;

    public RuleTemplate() {
    }

    public RuleTemplate(String groupId, String name, List<String> parameters, String template) {
        this.groupId = groupId;
        this.name = name;
        this.parameters = parameters;
        this.template = template;
    }

    public String getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
