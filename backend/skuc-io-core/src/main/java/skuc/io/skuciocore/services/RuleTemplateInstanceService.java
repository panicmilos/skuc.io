package skuc.io.skuciocore.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.templates.RuleTemplateInstance;
import skuc.io.skuciocore.persistence.RuleTemplateInstanceRepository;

@Service
public class RuleTemplateInstanceService extends CrudService<RuleTemplateInstance> {

  private final LocationService _locationService;
  private final RuleTemplateService _ruleTemplateService;
  private final RuleTemplateInstanceRepository _ruleTemplateInstanceRepository;

  @Autowired
  public RuleTemplateInstanceService(
    LocationService locationService,
    RuleTemplateService ruleTemplateService,
    RuleTemplateInstanceRepository ruleTemplateInstanceRepository
  ) {
    super(ruleTemplateInstanceRepository);
    _locationService = locationService;
    _ruleTemplateService = ruleTemplateService;
    _ruleTemplateInstanceRepository = ruleTemplateInstanceRepository;
  }

  public Collection<RuleTemplateInstance> getByTemplate(String templateId) {
    return _ruleTemplateInstanceRepository.getByTemplate(templateId);
  }

  @Override
  public RuleTemplateInstance create(RuleTemplateInstance ruleTemplateInstance) {
    _locationService.getOrThrow(ruleTemplateInstance.getLocationId());
    _ruleTemplateService.getOrThrow(ruleTemplateInstance.getTemplateId());

    var ruleTemplateInstanceWithSameValues = _ruleTemplateInstanceRepository.getByTemplateAndLocationAndValues(
      ruleTemplateInstance.getTemplateId(),
      ruleTemplateInstance.getLocationId(),
      ruleTemplateInstance.getValues()
    );

    if (ruleTemplateInstanceWithSameValues != null) {
      throw new BadLogicException("Rule template with the same values for given location is already instantiated.");
    }

    return super.create(ruleTemplateInstance);
  }

  @Override
  public RuleTemplateInstance update(RuleTemplateInstance ruleTemplateInstance) {
    var existingRuleTemplateInstance = getOrThrow(ruleTemplateInstance.getId());

    var ruleTemplateInstanceWithSameValues = _ruleTemplateInstanceRepository.getByTemplateAndLocationAndValues(
      existingRuleTemplateInstance.getTemplateId(),
      existingRuleTemplateInstance.getLocationId(),
      ruleTemplateInstance.getValues()
    );

    if (ruleTemplateInstanceWithSameValues != null && !ruleTemplateInstanceWithSameValues.getId().equals(existingRuleTemplateInstance.getId())) {
      throw new BadLogicException("Rule template with the same values for given location is already instantiated.");
    }

    return super.update(ruleTemplateInstance);
  } 

}
