package skuc.io.skuciocore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.bus.Bus;
import skuc.io.skuciocore.models.events.kjar.EventOccured;
import skuc.io.skuciocore.models.notifications.InformUserNotification;

@Service
public class NotificationService {
  
  private final Bus _bus;

  @Autowired
  public NotificationService(Bus bus) {
    _bus = bus;
  }

  public void sendFrom(EventOccured eventOccured) {
    if (eventOccured.getName().equals("InformUser")) {
      informUser(eventOccured);
    }
  }

  private void informUser(EventOccured eventOccured) {
    var notification = new InformUserNotification(eventOccured.getParam("about"), eventOccured.getParam("groupId"));

    _bus.fire("InformUser", notification);
  }
}
