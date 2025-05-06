package chan.shop.goodsread.service.event.handler;

import chan.shop.event.Event;
import chan.shop.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean supports(Event<T> event);
}
