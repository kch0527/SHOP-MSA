package chan.shop.goodsread.service.event.handler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsCreatedEventPayload;
import chan.shop.goodsread.repository.GoodsQueryModel;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class GoodsCreateEventHandler implements EventHandler<GoodsCreatedEventPayload>{
    private final GoodsQueryModelRepository goodsQueryModelRepository;

    @Override
    public void handle(Event<GoodsCreatedEventPayload> event) {
        GoodsCreatedEventPayload payload = event.getPayload();
        goodsQueryModelRepository.create(
                GoodsQueryModel.create(payload),
                Duration.ofDays(1)
        );
    }

    @Override
    public boolean supports(Event<GoodsCreatedEventPayload> event) {
        return EventType.GOODS_CREATED == event.getType();
    }
}
