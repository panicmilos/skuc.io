package skuc.io.skucioapp.controllers;

import java.util.Collection;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.api_contracts.requests.TemplateInstances.CreateTemplateInstanceRequest;
import skuc.io.skucioapp.utils.ScriptExecutor;
import skuc.io.skuciocore.models.csm.templates.RuleTemplateInstance;
import skuc.io.skuciocore.services.RuleTemplateInstanceService;
import skuc.io.skuciocore.utils.ClassScannerUtils;

@RestController
@RequestMapping("groups")
public class RuleTemplateInstancesController {

  private final RuleTemplateInstanceService _ruleTemplateInstanceService;
  private final ModelMapper _mapper;

  @Autowired
  public RuleTemplateInstancesController(RuleTemplateInstanceService ruleTemplateInstanceService, ModelMapper mapper) {
    _ruleTemplateInstanceService = ruleTemplateInstanceService;
    _mapper = mapper;
  }

  @GetMapping("{groupId}/templates/{templateId}/instances")
  public ResponseEntity<Collection<RuleTemplateInstance>> getTemplateInstances(@PathVariable String templateId) {
    return ResponseEntity.ok(_ruleTemplateInstanceService.getByTemplate(templateId));
  }

  @PostMapping("{groupId}/templates/{templateId}/instances")
  public ResponseEntity<RuleTemplateInstance> createTemplateInstance(@PathVariable String groupId, @PathVariable String templateId, @RequestBody CreateTemplateInstanceRequest request) {
    var ruleTemplateInstance = _mapper.map(request, RuleTemplateInstance.class);
    ruleTemplateInstance.setId(UUID.randomUUID().toString());
    ruleTemplateInstance.setGroupId(groupId);
    ruleTemplateInstance.setTemplateId(templateId);

    var createdRuleTemplate = _ruleTemplateInstanceService.create(ruleTemplateInstance);

    recompileKjar();
      
    return ResponseEntity.ok(createdRuleTemplate);
  }

  @DeleteMapping("{groupId}/templates/{templateId}/instances/{instanceId}")
  public ResponseEntity<RuleTemplateInstance> deleteTemplateInstance(@PathVariable String instanceId) {
    var deletedRuleTemplateInstance = _ruleTemplateInstanceService.delete(instanceId);

    recompileKjar();
      
    return ResponseEntity.ok(deletedRuleTemplateInstance);
  }

  private void recompileKjar() {
    var command = ClassScannerUtils.getWorkingPath() + "\\recompileRules.bat";
    System.out.println(command);
    ScriptExecutor.Execute(ClassScannerUtils.getWorkingPath() + "\\recompileRules.bat");
  }
  
}
