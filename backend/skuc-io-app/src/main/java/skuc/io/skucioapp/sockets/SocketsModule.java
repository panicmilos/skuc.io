package skuc.io.skucioapp.sockets;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import skuc.io.skuciocore.models.notifications.Notification;

@Component
public class SocketsModule {

  private final SocketIOServer _server;
  private final Map<String, SocketIONamespace> _namespaces;

  @Autowired
  public SocketsModule(SocketIOServer server) {
    this._server = server;
    this._namespaces = new HashMap<>();

  }

  public void tryToRegisterNameSpace(String namespaceName) {
    var namespacePath = "/" + namespaceName;
    if (_namespaces.containsKey(namespacePath)) {
      return;
    }

    var namespace = _server.addNamespace(namespacePath);
    
    namespace.addConnectListener(onConnected());
    namespace.addDisconnectListener(onDisconnected());
    
    _namespaces.put(namespaceName, namespace);
  }

  private ConnectListener onConnected() {
    return client -> {
      HandshakeData handshakeData = client.getHandshakeData();
      System.out.format("Client[%s] - Connected to sockets module through '%s'", client.getSessionId().toString(),
          handshakeData.getUrl());
    };
  }

  private DisconnectListener onDisconnected() {
    return client -> System.out.format("Client[%s] - Disconnected from sockets module.", client.getSessionId().toString());
  }

  public void brodcast(String namespaceName, Notification notification) {
    var namespace = _namespaces.get(namespaceName);

    if(namespace == null) {
      return;
    }

    namespace.getBroadcastOperations().sendEvent("event", notification);
  }

}
