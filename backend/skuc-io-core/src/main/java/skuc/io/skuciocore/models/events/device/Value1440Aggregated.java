package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;
import java.util.UUID;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timeStamp")
public class Value1440Aggregated extends ValueAggregated {

  public Value1440Aggregated(String deviceId, String deviceType, String paramName, Aggregate aggregate) {
    super(UUID.randomUUID().toString(), deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType, paramName, 1440, aggregate);
  }

}
  