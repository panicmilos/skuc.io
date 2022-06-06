package skuc.io.skuciocore.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.templates.RuleTemplateInstance;

@Repository
public class RuleTemplateInstanceRepository extends CrudRepository<RuleTemplateInstance> {

  public RuleTemplateInstanceRepository() {
    super(RuleTemplateInstance.class);  
  }

  public Collection<RuleTemplateInstance> getByTemplate(String templateId) {
    try (var session = getSession()) {
      return session.query(this.concreteClass).whereEquals("templateId", templateId).toList();
    }
  }

  public RuleTemplateInstance getByTemplateAndLocationAndValues(String templateId, String locationId, ArrayList<Object> values) {
    // TODO: FECOVATI SVE OVAKO A ONDA FOR PETLJOM PROVERITI VALUES
    try (var session = getSession()) {
      return session.query(this.concreteClass)
        .whereEquals("templateId", templateId)
        .whereEquals("locationId", locationId)
        // .whereEquals("values", values)
        .firstOrDefault();
    }
  }

  @Override
  public void update(RuleTemplateInstance ruleTemplateInstance) {
    try (var session = getSession()) {
      var existingRuleTemplate = session.load(concreteClass, ruleTemplateInstance.getId());

      existingRuleTemplate.setValues(ruleTemplateInstance.getValues());

      session.saveChanges();
    }
  }



}