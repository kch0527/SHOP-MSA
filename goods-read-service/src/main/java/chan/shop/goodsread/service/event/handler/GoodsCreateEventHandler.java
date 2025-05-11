package chan.shop.goodsread.service.event.handler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsCreatedEventPayload;
import chan.shop.goodsread.repository.BrandGoodsCountRepository;
import chan.shop.goodsread.repository.GoodsIdListRepository;
import chan.shop.goodsread.repository.GoodsQueryModel;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class GoodsCreateEventHandler implements EventHandler<GoodsCreatedEventPayload>{
    private final GoodsQueryModelRepository goodsQueryModelRepository;
    private final GoodsIdListRepository goodsIdListRepository;
    private final BrandGoodsCountRepository brandGoodsCountRepository;

    @Override
    public void handle(Event<GoodsCreatedEventPayload> event) {
        GoodsCreatedEventPayload payload = event.getPayload();
        goodsQueryModelRepository.create(
                GoodsQueryModel.create(payload),
                Duration.ofDays(1)
        );
        goodsIdListRepository.add(payload.getBrandId(), payload.getGoodsId(), 1000L);
        brandGoodsCountRepository.createOrUpdate(payload.getBrandId(), payload.getBrandGoodsCount());
    }

    @Override
    public boolean supports(Event<GoodsCreatedEventPayload> event) {
        return EventType.GOODS_CREATED == event.getType();
    }
}
