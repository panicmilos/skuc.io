package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;
import java.util.UUID;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timeStamp")
public class Value0005Aggregated extends ValueAggregated {

  public Value0005Aggregated(String deviceId, String deviceType, String paramName, Aggregate aggregate) {
    super(UUID.randomUUID().toString(), deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType, paramName, 5, 0, aggregate);
  }
  

  // TODO: REMOVe
  public Value0005Aggregated(String parentId, String id, String deviceId, String deviceType, String paramName, Aggregate aggregate) {
    super(parentId, id, deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType, paramName, 5, 0, aggregate);
  }

  

}
