package skuc.io.skuciocore.ksessions;

import java.time.LocalDateTime;

import org.kie.api.runtime.KieSession;

import skuc.io.skuciocore.models.events.device.Aggregate;
import skuc.io.skuciocore.models.events.device.Value0005Aggregated;
import skuc.io.skuciocore.models.events.device.Value0015Aggregated;
import skuc.io.skuciocore.models.events.device.Value0030Aggregated;
import skuc.io.skuciocore.models.events.device.Value0060Aggregated;

public class AggregationSessionSeeder {
  
  private static final String DEVICE_ID = "953164df-0432-43e7-b735-a204dfcda3ed";
  private static final String DEVICE_TYPE = "Temperature Sensor";
  private static final String PARAM_NAME = "temp";

  public static void insertValue0005Aggregated(KieSession session, String parentId, String id, Double min, Double max, Double sum, Double average, Long count) {
    session.insert((new Value0005Aggregated(parentId, id, DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }
  
  public static void insertValue0015Aggregated(KieSession session, String parentId, String id, Double min, Double max, Double sum, Double average, Long count, int addMinutes) {
    var agr = (new Value0015Aggregated(parentId, id, DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count)));
    agr.setCreatedAt(LocalDateTime.now().plusMinutes(addMinutes));
    session.insert(agr);
  }
  
  public static void insertValue0030Aggregated(KieSession session, String parentId, String id, Double min, Double max, Double sum, Double average, Long count, int addMinutes) {
    var agr = new Value0030Aggregated(parentId, id, DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count));
    agr.setCreatedAt(LocalDateTime.now().plusMinutes(addMinutes));
    session.insert(agr);
  }

  public static void insertValue0060Aggregated(KieSession session, String parentId, String id, Double min, Double max, Double sum, Double average, Long count) {
    session.insert((new Value0060Aggregated(parentId, id, DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }
}
