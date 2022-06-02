package skuc.io.skucioapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skuc.io.skucioapp.sockets.SocketsModule;

@RestController
@RequestMapping("sockets/")
public class SocketsController {

  private final SocketsModule _socketsModule;

  @Autowired
  public SocketsController(SocketsModule socketsModule) {
    _socketsModule = socketsModule;
  }

  @PostMapping("{groupId}/infos/initialization")
  public ResponseEntity<Object> initializeInfos(@PathVariable String groupId) {
    var namespace = groupId + "/infos";

    _socketsModule.tryToRegisterNameSpace(namespace);

    return ResponseEntity.ok(null);
  }
  
}
