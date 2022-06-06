package skuc.io.skuciocore.models.events.device;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("timeStamp")
public class ValueReceived extends DeviceEvent {
  private String paramName;
  private float value;
  private Date timeStamp = new Date();

  public ValueReceived() {
    super();
    super.setId(UUID.randomUUID().toString());
  }

  public ValueReceived(ValueReceived copy) {
    setCreatedAt(copy.getCreatedAt());
    setDeviceType(copy.getDeviceType());
    setId(copy.getId());
    setOccuredAt(copy.getOccuredAt());
    setParamName(copy.getParamName());
    setStreamId(copy.getStreamId());
    setTimeStamp(copy.getTimeStamp());
    setType(copy.getType());
    setValue(copy.getValue());
  }

  public ValueReceived(String id, String deviceId, LocalDateTime createdAt, LocalDateTime occuredAt, String deviceType,
      String paramName, float value) {
    super(id, deviceId, createdAt, occuredAt, deviceType);
    this.paramName = paramName;
    this.value = value;
  }

  public ValueReceived(String id, String deviceId, String deviceType) {
    super(id, deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType);
  }

  public ValueReceived(String deviceId, String deviceType, String paramName, float value) {
    super(UUID.randomUUID().toString(), deviceId, LocalDateTime.now(), LocalDateTime.now(), deviceType);
    this.paramName = paramName;
    this.value = value;
  }

  public String getDeviceId() {
    return this.getStreamId();
  }

  public String getParamName() {
    return this.paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public float getValue() {
    return this.value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  public Date getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

}
