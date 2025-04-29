package chan.shop.hotgoodsservice.service.eventhandler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsLikeEventPayload;
import chan.shop.hotgoodsservice.repository.GoodsLikeCountRepository;
import chan.shop.hotgoodsservice.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsLikedEventHandler implements EventHandler<GoodsLikeEventPayload>{
    private final GoodsLikeCountRepository goodsLikeCountRepository;

    @Override
    public void handle(Event<GoodsLikeEventPayload> event) {
        GoodsLikeEventPayload payload = event.getPayload();
        goodsLikeCountRepository.createOrUpdate(
                payload.getGoodsId(),
                payload.getGoodsLikeCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<GoodsLikeEventPayload> event) {
        return EventType.GOODS_LIKED == event.getType();
    }

    @Override
    public Long findGoodsId(Event<GoodsLikeEventPayload> event) {
        return event.getPayload().getGoodsId();
    }
}
