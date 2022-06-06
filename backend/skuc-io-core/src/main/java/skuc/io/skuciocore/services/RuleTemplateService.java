package skuc.io.skuciocore.services;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.drools.compiler.compiler.DrlParser;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.lang.DrlDumper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.templates.RuleTemplate;
import skuc.io.skuciocore.persistence.RuleTemplateRepository;
import skuc.io.skuciocore.utils.ClassScannerUtils;


@Service
public class RuleTemplateService extends CrudService<RuleTemplate> {
    
    private GroupService _groupService;
    private RuleTemplateRepository _ruleTemplateRepository;
    private Map<String, String> _modelClasses;

    @Autowired
    public RuleTemplateService(RuleTemplateRepository repository, GroupService groupService) {
        super(repository);
        _groupService = groupService;
        _ruleTemplateRepository = repository;
        _modelClasses =  ClassScannerUtils.findAllClasses("skuc.io.skuciocore.models");
    }

    public RuleTemplate create(String groupId, String name, List<String> parameters, String when, String then) {
        var templatesDsl = loadTemplatesDsl();
        var composedRule = composeRule(when, then);

        var parsedRule = parseRule(composedRule, templatesDsl);
        var imports = generateImports(parsedRule);
        var template = composeTemplate(parameters, parsedRule, name, imports);

        // var dataProvider = new ArrayDataProvider(new String[][] {
        //     new String[] {"Peric"}
        // });

        // var targetStream = new ByteArrayInputStream(template.getBytes());

        // var converter = new DataProviderCompiler();
        // var drl = converter.compile(dataProvider, targetStream);

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

    private String composeTemplate(List<String> parameters, String rule, String name, String imports) {
        var sb = new StringBuilder();

        sb = sb.append("template header\n");
        for (int i = 0; i < parameters.size(); i++) {
            sb.append(parameters.get(i) + "\n");
            if (i == parameters.size() - 1) {
                sb.append("\n");
            }
        }

        rule = rule.replace("package skuc.io", "package skuc.io \n\n" + imports + "\n\n");

        var ruleIndex = rule.indexOf("rule");
        var beforeRule = rule.substring(0, ruleIndex);
        var afterRule = rule.substring(ruleIndex);
        beforeRule = beforeRule + "\ntemplate \"" + name + "\"\n\n";
        rule = beforeRule + afterRule;

        sb.append(rule);
        sb.append("\nend template");

        return sb.toString();
    }

    private String composeRule(String when, String then) {
        var sb = new StringBuilder();

        sb = sb.append("paketic skuc.io;\n\n")
               .append("rule \"RuleNamePlaceholder\"\n")
               .append("when\n")
               .append(when + "\n")
               .append("then\n")
               .append(then + "\n")
               .append("end ");

        return sb.toString();
    }

    private String generateImports(String rulePart) {
        var potentialClasses = findAllPotentialClasses(rulePart);

        var sb = new StringBuilder();
        for (var potentialClass : potentialClasses) {
            if (_modelClasses.containsKey(potentialClass)) {
                sb.append("import " + _modelClasses.get(potentialClass) + ";\n");
            }
        }

        return sb.toString();
    }

    private Set<String> findAllPotentialClasses(String value) {
        var wordsSet = new HashSet<String>();
        var start_index = -1;
        for (var i = 0; i < value.length(); i++) {
            var currChar = value.charAt(i);
            if (Character.isUpperCase(currChar) && start_index == -1) {
                start_index = i;
            }

            if ((!Character.isLetter(currChar) || i == value.length() - 1) && start_index != -1) {
                wordsSet.add(value.substring(start_index, i));
                start_index = -1;
            }
        }

        return wordsSet;
    }

    private Reader loadTemplatesDsl() {
        var classloader = Thread.currentThread().getContextClassLoader();
        var templatesDsl = classloader.getResourceAsStream("templates.dsl");

        return new InputStreamReader(templatesDsl);
    }

    public Collection<RuleTemplate> getByGroup(String groupId) {
        return _ruleTemplateRepository.getByGroup(groupId);
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
