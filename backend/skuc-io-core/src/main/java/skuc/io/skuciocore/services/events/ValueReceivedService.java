package skuc.io.skuciocore.services.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.events.device.ValueReceived;
import skuc.io.skuciocore.persistence.events.ValueReceivedRepository;

@Service
public class ValueReceivedService extends CrudEventsService<ValueReceived> {
  
  @Autowired
  public ValueReceivedService(ValueReceivedRepository repository) {
    super(repository);
  }

  
}
