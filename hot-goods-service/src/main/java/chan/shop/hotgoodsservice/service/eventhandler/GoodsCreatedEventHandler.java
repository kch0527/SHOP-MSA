package chan.shop.hotgoodsservice.service.eventhandler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsCreatedEventPayload;
import chan.shop.hotgoodsservice.repository.GoodsCreatedTimeRepository;
import chan.shop.hotgoodsservice.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsCreatedEventHandler implements EventHandler<GoodsCreatedEventPayload>{
    private final GoodsCreatedTimeRepository goodsCreatedTimeRepository;

    @Override
    public void handle(Event<GoodsCreatedEventPayload> event) {
        GoodsCreatedEventPayload payload = event.getPayload();
        goodsCreatedTimeRepository.createOrUpdate(
                payload.getGoodsId(),
                payload.getCreateAt(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<GoodsCreatedEventPayload> event) {
        return EventType.GOODS_CREATED == event.getType();
    }

    @Override
    public Long findGoodsId(Event<GoodsCreatedEventPayload> event) {
        return event.getPayload().getGoodsId();
    }
}
