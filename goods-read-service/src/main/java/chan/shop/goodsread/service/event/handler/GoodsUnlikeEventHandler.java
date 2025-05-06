package chan.shop.goodsread.service.event.handler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsLikeEventPayload;
import chan.shop.event.payload.GoodsUnlikeEventPayload;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsUnlikeEventHandler implements EventHandler<GoodsUnlikeEventPayload>{
    private final GoodsQueryModelRepository goodsQueryModelRepository;

    @Override
    public void handle(Event<GoodsUnlikeEventPayload> event) {
        goodsQueryModelRepository.read(event.getPayload().getGoodsId())
                .ifPresent(goodsQueryModel -> {
                    goodsQueryModel.update(event.getPayload());
                    goodsQueryModelRepository.update(goodsQueryModel);
                });
    }

    @Override
    public boolean supports(Event<GoodsUnlikeEventPayload> event) {
        return EventType.GOODS_UNLIKED == event.getType();
    }
}
