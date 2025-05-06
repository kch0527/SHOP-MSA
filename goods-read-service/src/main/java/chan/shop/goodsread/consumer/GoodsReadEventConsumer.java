package chan.shop.goodsread.consumer;

import chan.shop.event.Event;
import chan.shop.event.EventPayload;
import chan.shop.event.EventType;
import chan.shop.goodsread.service.GoodsReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoodsReadEventConsumer {
    private final GoodsReadService goodsReadService;

    @KafkaListener(topics = {
            EventType.Topic.CHAN_SHOP_GOODS,
            EventType.Topic.CHAN_SHOP_COMMENT,
            EventType.Topic.CHAN_SHOP_LIKE
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[GoodsReadEventConsumer.listen] message={}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if (event != null) {
            goodsReadService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
