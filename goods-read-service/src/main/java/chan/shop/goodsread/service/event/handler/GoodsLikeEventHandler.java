package chan.shop.goodsread.service.event.handler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.CommentDeletedEventPayload;
import chan.shop.event.payload.GoodsLikeEventPayload;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsLikeEventHandler implements EventHandler<GoodsLikeEventPayload>{
    private final GoodsQueryModelRepository goodsQueryModelRepository;

    @Override
    public void handle(Event<GoodsLikeEventPayload> event) {
        goodsQueryModelRepository.read(event.getPayload().getGoodsId())
                .ifPresent(goodsQueryModel -> {
                    goodsQueryModel.update(event.getPayload());
                    goodsQueryModelRepository.update(goodsQueryModel);
                });
    }

    @Override
    public boolean supports(Event<GoodsLikeEventPayload> event) {
        return EventType.GOODS_LIKED == event.getType();
    }
}
