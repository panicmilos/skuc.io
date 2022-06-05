package skuc.io.skucioapp.api_contracts.requests.Templates;

import java.util.List;

public class CreateTemplateRequest {
    private String name;
    private List<String> parameters;
    private String when;
    private String then;

    public CreateTemplateRequest() {}

    public CreateTemplateRequest(String name, List<String> parameters, String when, String then) {
        this.name = name;
        this.parameters = parameters;
        this.when = when;
        this.then = then;
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

    public String getWhen() {
        return this.when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getThen() {
        return this.then;
    }

    public void setThen(String then) {
        this.then = then;
    }
}
