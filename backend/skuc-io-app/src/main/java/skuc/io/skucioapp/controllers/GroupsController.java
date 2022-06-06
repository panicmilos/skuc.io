package skuc.io.skucioapp.controllers;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.api_contracts.requests.Groups.CreateGroupRequest;
import skuc.io.skuciocore.models.csm.Group;
import skuc.io.skuciocore.seeder.ContextSeeder;
import skuc.io.skuciocore.services.GroupService;

@RestController
@RequestMapping("groups")
public class GroupsController {
  
  private final GroupService _groupService;
  private final ContextSeeder _contextSeeder;
  private final ModelMapper _mapper;

  @Autowired
  public GroupsController(GroupService groupService, ContextSeeder contextSeeder, ModelMapper mapper) {
    _contextSeeder = contextSeeder;
    _groupService = groupService;
    _mapper = mapper;
  }

  @GetMapping
  public ResponseEntity<Collection<Group>> getGroups() {
    return ResponseEntity.ok(_groupService.get());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Group> getGroup(@PathVariable String id) {
    return ResponseEntity.ok(_groupService.getOrThrow(id));
  }

  @PostMapping
  public ResponseEntity<Group> createGroup(@RequestBody CreateGroupRequest request) {
    var group = _mapper.map(request, Group.class);
    var createdGroup = _groupService.create(group);

    _contextSeeder.seed(createdGroup.getId());

    return ResponseEntity.ok(createdGroup);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Group> updateGroup(@PathVariable String id, @RequestBody CreateGroupRequest request) {
    var group = _mapper.map(request, Group.class);
    group.setId(id);

    return ResponseEntity.ok(_groupService.update(group));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Group> deleteGroup(@PathVariable String id) {
    return ResponseEntity.ok(_groupService.delete(id));
  }

}
