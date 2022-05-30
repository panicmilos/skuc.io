package skuc.io.skuciocore.persistence.events;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.events.device.ValueReceived;

@Repository
public class ValueReceivedRepository extends CrudEventsRepository<ValueReceived> {
  
  public ValueReceivedRepository() {
    super(ValueReceived.class);
  }
}
