package skuc.io.skuciocore.persistence.events;

import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.events.device.StatusReceived;

@Repository
public class StatusReceivedRepository extends CrudEventsRepository<StatusReceived> {
  
  public StatusReceivedRepository() {
    super(StatusReceived.class);
  }
}
