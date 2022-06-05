package skuc.io.skuciocore.services;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.drools.compiler.compiler.DrlParser;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.lang.DrlDumper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.template.RuleTemplate;
import skuc.io.skuciocore.persistence.RuleTemplateRepository;


@Service
public class RuleTemplateService extends CrudService<RuleTemplate> {
    
    private GroupService _groupService;
    private RuleTemplateRepository _ruleTemplateRepository;

    @Autowired
    public RuleTemplateService(GroupService _groupService, RuleTemplateRepository repository) {
        super(repository);
        _ruleTemplateRepository = repository;
    }

    public RuleTemplate create(String groupId, String name, List<String> parameters, String when, String then) {
        var templatesDsl = loadTemplatesDsl();
        var composedRule = composeRule(name, when, then);

        var parsedRule = parseRule(composedRule, templatesDsl);
        var template = composeTemplate(parameters, parsedRule);

        var ruleTemplate = new RuleTemplate(groupId, name, parameters, template);
        return create(ruleTemplate);
    }

    private String parseRule(String rule, Reader templatesDsl) {
        try {
            var parser = new DrlParser();
            var packageDescr = parser.parse(rule, templatesDsl);

            var drlDumper = new DrlDumper();
            var parsedRule = drlDumper.dump(packageDescr);

            var correctedRule = parsedRule.replaceAll("template_start", "@{")
                                          .replaceAll("template_end", "}");
            return correctedRule;
        } catch (DroolsParserException e) {
            throw new BadLogicException("Rule is not in correct format!");
        }
    }

    private String composeTemplate(List<String> parameters, String rule) {
        var sb = new StringBuilder();

        sb = sb.append("template header\n");
        for (int i = 0; i < parameters.size(); i++) {
            sb.append(parameters.get(i) + "\n");
            if (i == parameters.size() - 1) {
                sb.append("\n");
            }
        }

        sb.append(rule);

        return sb.toString();
    }

    private String composeRule(String name, String when, String then) {
        var sb = new StringBuilder();

        sb = sb.append("paketic skuc.io;\n\n")
               .append("use most common imports;\n\n")
               .append("> template \"" + name +  "\"\n\n")
               .append("rule \"" + name +  "\"\n")
               .append("when\n")
               .append(when + "\n")
               .append("then\n")
               .append(then + "\n")
               .append("end;")
               .append("> end template");

        return sb.toString();
    }

    private Reader loadTemplatesDsl() {
        var classloader = Thread.currentThread().getContextClassLoader();
        var templatesDsl = classloader.getResourceAsStream("templates.dsl");

        return new InputStreamReader(templatesDsl);
    }

    @Override
    public RuleTemplate create(RuleTemplate ruleTemplate) {
        _groupService.getOrThrow(ruleTemplate.getGroupId());

        var ruleTemplateWithSameName = _ruleTemplateRepository.getByGroupAndName(ruleTemplate.getGroupId(), ruleTemplate.getName());
        if (ruleTemplateWithSameName != null) {
            throw new BadLogicException("Rule template with the same name already exists in the group.");
        }
    
        return super.create(ruleTemplate);
    }

    
    @Override
    public RuleTemplate update(RuleTemplate ruleTemplate) {
        var existingRuleTemplate = getOrThrow(ruleTemplate.getId());

        var ruleTemplateWithSameName = _ruleTemplateRepository.getByGroupAndName(ruleTemplate.getGroupId(), ruleTemplate.getName());
        if (ruleTemplateWithSameName != null && !ruleTemplateWithSameName.getId().equals(existingRuleTemplate.getId())) {
            throw new BadLogicException("Rule template with the same name already exists in the group.");
        }

        return super.update(ruleTemplate);
    }
}
