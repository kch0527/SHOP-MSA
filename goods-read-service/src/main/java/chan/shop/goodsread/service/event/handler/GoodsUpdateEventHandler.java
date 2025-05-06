package chan.shop.goodsread.service.event.handler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsCreatedEventPayload;
import chan.shop.event.payload.GoodsUpdatedEventPayload;
import chan.shop.goodsread.repository.GoodsQueryModel;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class GoodsUpdateEventHandler implements EventHandler<GoodsUpdatedEventPayload>{
    private final GoodsQueryModelRepository goodsQueryModelRepository;

    @Override
    public void handle(Event<GoodsUpdatedEventPayload> event) {
        goodsQueryModelRepository.read(event.getPayload().getGoodsId())
                .ifPresent(goodsQueryModel -> {
                    goodsQueryModel.update(event.getPayload());
                    goodsQueryModelRepository.update(goodsQueryModel);
                });
    }

    @Override
    public boolean supports(Event<GoodsUpdatedEventPayload> event) {
        return EventType.GOODS_UPDATED == event.getType();
    }
}
