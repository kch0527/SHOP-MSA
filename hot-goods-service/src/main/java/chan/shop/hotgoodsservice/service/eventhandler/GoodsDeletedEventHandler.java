package chan.shop.hotgoodsservice.service.eventhandler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsDeletedEventPayload;
import chan.shop.hotgoodsservice.repository.GoodsCreatedTimeRepository;
import chan.shop.hotgoodsservice.repository.HotGoodsListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsDeletedEventHandler implements EventHandler<GoodsDeletedEventPayload>{
    private final HotGoodsListRepository hotGoodsListRepository;
    private final GoodsCreatedTimeRepository goodsCreatedTimeRepository;

    @Override
    public void handle(Event<GoodsDeletedEventPayload> event) {
        GoodsDeletedEventPayload payload = event.getPayload();
        goodsCreatedTimeRepository.delete(payload.getGoodsId());
        hotGoodsListRepository.remove(payload.getGoodsId(), payload.getCreateAt());
    }

    @Override
    public boolean supports(Event<GoodsDeletedEventPayload> event) {
        return EventType.GOODS_DELETED == event.getType();
    }

    @Override
    public Long findGoodsId(Event<GoodsDeletedEventPayload> event) {
        return event.getPayload().getGoodsId();
    }
}
