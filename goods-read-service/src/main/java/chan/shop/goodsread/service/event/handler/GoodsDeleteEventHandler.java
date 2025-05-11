package chan.shop.goodsread.service.event.handler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsDeletedEventPayload;
import chan.shop.event.payload.GoodsUpdatedEventPayload;
import chan.shop.goodsread.repository.BrandGoodsCountRepository;
import chan.shop.goodsread.repository.GoodsIdListRepository;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsDeleteEventHandler implements EventHandler<GoodsDeletedEventPayload>{
    private final GoodsQueryModelRepository goodsQueryModelRepository;
    private final GoodsIdListRepository goodsIdListRepository;
    private final BrandGoodsCountRepository brandGoodsCountRepository;

    @Override
    public void handle(Event<GoodsDeletedEventPayload> event) {
        GoodsDeletedEventPayload payload = event.getPayload();
        goodsIdListRepository.delete(payload.getBrandId(), payload.getGoodsId());
        goodsQueryModelRepository.delete(payload.getGoodsId());
        brandGoodsCountRepository.createOrUpdate(payload.getBrandId(), payload.getBrandGoodsCount());
    }

    @Override
    public boolean supports(Event<GoodsDeletedEventPayload> event) {
        return EventType.GOODS_DELETED == event.getType();
    }
}
