package chan.shop.hotgoodsservice.service.eventhandler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsViewEventPayload;
import chan.shop.hotgoodsservice.repository.GoodsViewCountRepository;
import chan.shop.hotgoodsservice.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsViewEventHandler implements EventHandler<GoodsViewEventPayload>{
    private final GoodsViewCountRepository goodsViewCountRepository;

    @Override
    public void handle(Event<GoodsViewEventPayload> event) {
        GoodsViewEventPayload payload = event.getPayload();
        goodsViewCountRepository.createOrUpdate(
                payload.getGoodsId(),
                payload.getGoodsViewCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<GoodsViewEventPayload> event) {
        return EventType.GOODS_VIEWED == event.getType();
    }

    @Override
    public Long findGoodsId(Event<GoodsViewEventPayload> event) {
        return event.getPayload().getGoodsId();
    }
}
