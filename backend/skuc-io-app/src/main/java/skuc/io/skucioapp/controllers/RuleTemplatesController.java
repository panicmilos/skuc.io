package skuc.io.skucioapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("{groupId}/templates")
    public ResponseEntity<RuleTemplate> createTemplate(@PathVariable String groupId, @RequestBody CreateTemplateRequest createTemplateRequest) {
        var ruleTemplate = _ruleTemplateService.create(groupId, createTemplateRequest.getName(), createTemplateRequest.getParameters(),
            createTemplateRequest.getWhen(), createTemplateRequest.getThen());
        
        return ResponseEntity.ok(ruleTemplate);
    }
}
