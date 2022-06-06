package skuc.io.skuciocore.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.exceptions.BadLogicException;
import skuc.io.skuciocore.models.csm.templates.RuleTemplate;
import skuc.io.skuciocore.models.csm.templates.RuleTemplateInstance;
import skuc.io.skuciocore.persistence.RuleTemplateInstanceRepository;
import skuc.io.skuciocore.utils.ClassScannerUtils;

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

    var createdInstance = super.create(ruleTemplateInstance);
    activate(createdInstance);

    return createdInstance;
  }

  @Override
  public RuleTemplateInstance delete(String ruleTemplateInstanceId) {
    var ruleTemplateInstance = getOrThrow(ruleTemplateInstanceId);
    removeDRL(ruleTemplateInstance);

    return super.delete(ruleTemplateInstanceId);
  }

  public void activate(RuleTemplateInstance ruleTemplateInstance) {
    var ruleTemplate = _ruleTemplateService.getOrThrow(ruleTemplateInstance.getTemplateId());
    var template = transformTemplateToUniqueInstance(ruleTemplate, ruleTemplateInstance);

    var stringVals = ruleTemplateInstance.getValues().stream().map(val -> val.toString()).toArray(String[]::new);
    var dataProvider = new ArrayDataProvider(new String[][] {
      stringVals
    });

    var templateStream = new ByteArrayInputStream(template.getBytes());
    var converter = new DataProviderCompiler();
    var drl = converter.compile(dataProvider, templateStream);

    writeDRLToOutput(ruleTemplateInstance, drl);
  }

  private String transformTemplateToUniqueInstance(RuleTemplate ruleTemplate, RuleTemplateInstance ruleTemplateInstance) {
    var template = ruleTemplate.getTemplate().replace("RuleNamePlaceholder", ruleTemplateInstance.getId());
    template = template.replace("when", "when\nLocation(id == \"" + ruleTemplateInstance.getLocationId() + "\")\n");

    return template;
  }

  private void writeDRLToOutput(RuleTemplateInstance ruleTemplateInstance, String drl) {
    try {
      var templateInstancePath = Paths.get(getTemplateInstancePath(ruleTemplateInstance.getLocationId(), ruleTemplateInstance.getId()));
      Files.createDirectories(templateInstancePath.getParent());
      Files.write(templateInstancePath, drl.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void removeDRL(RuleTemplateInstance ruleTemplateInstance) {
    try {
      var templateInstancePath = Paths.get(getTemplateInstancePath(ruleTemplateInstance.getLocationId(), ruleTemplateInstance.getId()));
      Files.delete(templateInstancePath);
   } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getTemplateInstancePath(String locationId, String ruleTemplateInstanceId) {
    var workingPath = ClassScannerUtils.getWorkingPath();
    return workingPath + "/../skuc-io-kjar/src/main/resources" + "/template-instances/" + ruleTemplateInstanceId + ".drl";
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
