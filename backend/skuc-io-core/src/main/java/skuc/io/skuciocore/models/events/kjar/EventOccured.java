package skuc.io.skuciocore.models.events.kjar;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import skuc.io.skuciocore.models.events.BaseEvent;
import skuc.io.skuciocore.models.utilityClasses.KeyValue;

@Role(Role.Type.EVENT)
@Timestamp("timeStamp")
public class EventOccured extends BaseEvent {
  private String name;
  private HashMap<String, String> params = new HashMap<>();
  private boolean processedByActivator;
  private Date timeStamp = new Date();

  public EventOccured() {
    super();
  }

  public EventOccured(String name, KeyValue<String, String> ...pairs) {
    super();
    this.name = name;

    for (var pair : pairs) {
      params.put(pair.getKey(), pair.getValue());
    }
  }

  public EventOccured addParam(KeyValue<String, String> pair) {
    params.put(pair.getKey(), pair.getValue());

    return this;
  }

  public String getParam(String key) {
    return params.get(key);
  }


  public EventOccured(String id, String deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String name) {
    super(id, deviceId, createdAt, occuredAt);
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getProcessedByActivator() {
    return this.processedByActivator;
  }

  public void setProcessedByActivator(boolean processedByActivator) {
    this.processedByActivator = processedByActivator;
  }

  public HashMap<String, String> getParams() {
    return this.params;
  }

  public void setParams(HashMap<String, String> params) {
    this.params = params;
  }


  public boolean isProcessedByActivator() {
    return this.processedByActivator;
  }

  public Date getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }


}
