package chan.shop.hotgoodsservice.service.eventhandler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsUnlikeEventPayload;
import chan.shop.hotgoodsservice.repository.GoodsLikeCountRepository;
import chan.shop.hotgoodsservice.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsUnlikedEventHandler implements EventHandler<GoodsUnlikeEventPayload>{
    private final GoodsLikeCountRepository goodsLikeCountRepository;
    @Override
    public void handle(Event<GoodsUnlikeEventPayload> event) {
        GoodsUnlikeEventPayload payload = event.getPayload();
        goodsLikeCountRepository.createOrUpdate(
                payload.getGoodsId(),
                payload.getGoodsLikeCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<GoodsUnlikeEventPayload> event) {
        return EventType.GOODS_UNLIKED == event.getType();
    }

    @Override
    public Long findGoodsId(Event<GoodsUnlikeEventPayload> event) {
        return event.getPayload().getGoodsId();
    }
}
