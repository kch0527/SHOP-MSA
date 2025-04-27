package chan.shop.hotgoodsservice.consumer;

import chan.shop.event.Event;
import chan.shop.event.EventPayload;
import chan.shop.event.EventType;
import chan.shop.hotgoodsservice.service.HotGoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HotGoodsEventConsumer {
    private final HotGoodsService hotGoodsService;

    @KafkaListener(topics = {
            EventType.Topic.CHAN_SHOP_GOODS,
            EventType.Topic.CHAN_SHOP_LIKE,
            EventType.Topic.CHAN_SHOP_VIEW,
            EventType.Topic.CHAN_SHOP_COMMENT
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[HotGoodsEventConsumer.listen] received message={}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if(event != null) {
            hotGoodsService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
