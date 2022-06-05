package skuc.io.skuciocore.services.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.events.device.StatusReceived;
import skuc.io.skuciocore.persistence.events.StatusReceivedRepository;

@Service
public class StatusReceivedService extends CrudEventsService<StatusReceived> {
  
  @Autowired
  public StatusReceivedService(StatusReceivedRepository repository) {
    super(repository);
  }

  
}
