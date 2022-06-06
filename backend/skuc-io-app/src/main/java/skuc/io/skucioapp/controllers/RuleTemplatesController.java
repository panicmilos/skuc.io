package skuc.io.skucioapp.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.api_contracts.requests.Templates.CreateTemplateRequest;
import skuc.io.skuciocore.models.csm.templates.RuleTemplate;
import skuc.io.skuciocore.services.RuleTemplateService;

@RestController
@RequestMapping("groups")
public class RuleTemplatesController {

    private final RuleTemplateService _ruleTemplateService;
    
    @Autowired
    public RuleTemplatesController(RuleTemplateService ruleTemplateService) {
        _ruleTemplateService = ruleTemplateService;
    }

    @GetMapping("{groupId}/templates")
    public ResponseEntity<Collection<RuleTemplate>> getTemplates(@PathVariable String groupId) {
      return ResponseEntity.ok(_ruleTemplateService.getByGroup(groupId));
    }

    @PostMapping("{groupId}/templates")
    public ResponseEntity<RuleTemplate> createTemplate(@PathVariable String groupId, @RequestBody CreateTemplateRequest createTemplateRequest) {
        var ruleTemplate = _ruleTemplateService.create(groupId, createTemplateRequest.getName(), createTemplateRequest.getParameters(),
            createTemplateRequest.getWhen(), createTemplateRequest.getThen());
        
        return ResponseEntity.ok(ruleTemplate);
    }

    @DeleteMapping("{groupId}/templates/{templateId}")
    public ResponseEntity<RuleTemplate> deleteTemplate(@PathVariable String templateId) {
        var deletedRuleTemplate = _ruleTemplateService.delete(templateId);
        
        return ResponseEntity.ok(deletedRuleTemplate);
    }
}
