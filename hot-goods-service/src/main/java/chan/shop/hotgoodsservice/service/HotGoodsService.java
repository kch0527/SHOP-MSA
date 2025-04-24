package chan.shop.hotgoodsservice.service;

import chan.shop.event.Event;
import chan.shop.event.EventPayload;
import chan.shop.event.EventType;
import chan.shop.hotgoodsservice.client.GoodsClient;
import chan.shop.hotgoodsservice.repository.HotGoodsListRepository;
import chan.shop.hotgoodsservice.response.HotGoodsResponse;
import chan.shop.hotgoodsservice.service.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotGoodsService {
    private final GoodsClient goodsClient;
    private final List<EventHandler> eventHandlers;
    private final HotGoodsScoreUpdater hotGoodsScoreUpdater;
    private final HotGoodsListRepository hotGoodsListRepository;

    public void handleEvent(Event<EventPayload> event) {
        EventHandler<EventPayload> eventHandler = findEventHandler(event);
        if (eventHandler == null) {
            return;
        }

        if (isGoodsCreateOrDeleted(event)) {
            eventHandler.handle(event);
        } else {
            hotGoodsScoreUpdater.update(event, eventHandler);
        }
    }

    private EventHandler<EventPayload> findEventHandler(Event<EventPayload> event) {
        return eventHandlers.stream()
                .filter(eventHandler -> eventHandler.supports(event))
                .findAny()
                .orElse(null);
    }

    private boolean isGoodsCreateOrDeleted(Event<EventPayload> event) {
        return EventType.GOODS_CREATED == event.getType() || EventType.GOODS_DELETED == event.getType();
    }

    public List<HotGoodsResponse> readAll(String dateStr) {
        return hotGoodsListRepository.readALl(dateStr).stream()
                .map(goodsClient::read)
                .filter(Objects::nonNull)
                .map(HotGoodsResponse::from)
                .toList();
    }
}
