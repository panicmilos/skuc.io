package skuc.io.skuciocore.persistence;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.templates.RuleTemplate;

@Repository
public class RuleTemplateRepository extends CrudRepository<RuleTemplate> {
    
    public RuleTemplateRepository() {
        super(RuleTemplate.class);
    }

    public Collection<RuleTemplate> getByGroup(String groupId) {
        try (var session = getSession()) {
            return session.query(this.concreteClass).whereEquals("groupId", groupId).toList();
        }
    }

    public RuleTemplate getByGroupAndName(String groupId, String name) {
        try (var session = getSession()) {
            return session.query(concreteClass)
                .whereEquals("groupId", groupId)
                .whereEquals("name", name)
                .firstOrDefault();
        }
    }

    @Override
    public void update(RuleTemplate ruleTemplate) {
        try (var session = getSession()) {
            var existingRuleTemplate = session.load(concreteClass, ruleTemplate.getId());

            existingRuleTemplate.setName(ruleTemplate.getName());
            existingRuleTemplate.setParameters(ruleTemplate.getParameters());
            existingRuleTemplate.setTemplate(ruleTemplate.getTemplate());

            session.saveChanges();
        } 
    }
}