package chan.shop.goodsread.service.event.handler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsDeletedEventPayload;
import chan.shop.event.payload.GoodsUpdatedEventPayload;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsDeleteEventHandler implements EventHandler<GoodsDeletedEventPayload>{
    private final GoodsQueryModelRepository goodsQueryModelRepository;

    @Override
    public void handle(Event<GoodsDeletedEventPayload> event) {
        GoodsDeletedEventPayload payload = event.getPayload();
        goodsQueryModelRepository.delete(payload.getGoodsId());
    }

    @Override
    public boolean supports(Event<GoodsDeletedEventPayload> event) {
        return EventType.GOODS_DELETED == event.getType();
    }
}
