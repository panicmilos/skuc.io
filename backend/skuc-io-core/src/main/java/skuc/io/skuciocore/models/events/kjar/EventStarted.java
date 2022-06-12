package skuc.io.skuciocore.models.events.kjar;

import java.time.LocalDateTime;

public class EventStarted {
    private String eventId;
    private String eventName;
    private LocalDateTime startTime = LocalDateTime.now();
    private int refresh = 1;

    public EventStarted()  {
        super();
    }

    public EventStarted(String eventId, String eventName) {
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getRefresh() {
        return refresh;
    }

    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }
}
