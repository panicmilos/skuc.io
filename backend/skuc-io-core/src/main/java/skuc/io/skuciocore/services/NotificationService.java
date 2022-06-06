package skuc.io.skuciocore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.bus.Bus;
import skuc.io.skuciocore.models.events.device.StatusReceived;
import skuc.io.skuciocore.models.events.device.ValueReceived;
import skuc.io.skuciocore.models.events.kjar.EventOccured;
import skuc.io.skuciocore.models.notifications.InformUserNotification;
import skuc.io.skuciocore.models.notifications.StatusReceivedNotification;
import skuc.io.skuciocore.models.notifications.ValueReceivedNotification;

@Service
public class NotificationService {

  private final Bus _bus;
  private final DeviceService _deviceService;

  @Autowired
  public NotificationService(Bus bus, DeviceService deviceService) {
    _bus = bus;
    _deviceService = deviceService;
  }

  public void sendFrom(ValueReceived valueReceived) {
    var device = _deviceService.get(valueReceived.getDeviceId());
    var notification = new ValueReceivedNotification(valueReceived.getDeviceId(), valueReceived.getDeviceType(), valueReceived.getParamName(), valueReceived.getValue(), device.getGroupId());

    _bus.fire("ValueReceived", notification);
  }

  public void sendFrom(StatusReceived statusReceived) {
    var device = _deviceService.get(statusReceived.getDeviceId());
    var notification = new StatusReceivedNotification(statusReceived.getDeviceId(), statusReceived.getDeviceType(), statusReceived.getValue(), device.getGroupId());

    _bus.fire("StatusReceived", notification);
  }

  public void sendFrom(EventOccured eventOccured) {
    if (!eventOccured.getName().equals("InformUser")) {
      return;
    }

    var params = eventOccured.getParams().keySet().stream().filter(key -> key.startsWith("@param")).sorted().map(key -> eventOccured.getParam(key)).toArray();
    var notification = new InformUserNotification(String.format(eventOccured.getParam("about"), params), eventOccured.getParam("groupId"));

    _bus.fire("InformUser", notification);
  }
}
