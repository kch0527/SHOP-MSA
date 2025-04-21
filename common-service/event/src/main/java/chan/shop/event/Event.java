package chan.shop.event;

import chan.shop.dataserializer.DataSerializer;
import lombok.Getter;

@Getter
public class Event<T extends EventPayload> { //event 통신을 위한 class
    private Long eventId;
    private EventType type;
    private T payLoad;

    public static Event<EventPayload> of (Long eventId, EventType type, EventPayload payload) {
        Event<EventPayload> event = new Event<>();
        event.eventId = eventId;
        event.type = type;
        event.payLoad = payload;
        return event;
    }

    //JSON -> String
    public String toJson() {
        return DataSerializer.serialize(this);
    }

    //JSON -> Event
    public static Event<EventPayload> fromJson(String json) {
        EventRaw eventRaw = DataSerializer.deserialize(json, EventRaw.class);
        if(eventRaw == null) {
            return null;
        }
        // EventRaw -> Event
        Event<EventPayload> event = new Event<>();
        event.eventId = eventRaw.getEventId();
        event.type = EventType.from(eventRaw.getType());
        event.payLoad = DataSerializer.deserialize(eventRaw.getPayload(),event.type.getPayloadClass());
        return event;
    }

    @Getter
    private static class EventRaw {
        private Long eventId;
        private String type;
        private Object payload;
    }
}
