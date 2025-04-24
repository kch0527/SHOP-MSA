package chan.shop.hotgoodsservice.service;

import chan.shop.event.Event;
import chan.shop.event.EventPayload;
import chan.shop.hotgoodsservice.repository.GoodsCreatedTimeRepository;
import chan.shop.hotgoodsservice.repository.HotGoodsListRepository;
import chan.shop.hotgoodsservice.service.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class HotGoodsScoreUpdater {
    private final HotGoodsListRepository hotGoodsListRepository;
    private final HotGoodsScoreCalculator hotGoodsScoreCalculator;
    private final GoodsCreatedTimeRepository goodsCreatedTimeRepository;

    private static final long HOT_GOODS_COUNT = 10;
    private static final Duration HOT_GOODS_TTL = Duration.ofDays(7);

    public void update(Event<EventPayload> event, EventHandler<EventPayload> eventHandler) {
        Long goodsId = eventHandler.findGoodsId(event);
        LocalDateTime createdTime = goodsCreatedTimeRepository.read(goodsId);

        eventHandler.handle(event);

        long score = hotGoodsScoreCalculator.calculate(goodsId);
        hotGoodsListRepository.add(
                goodsId,
                createdTime,
                score,
                HOT_GOODS_COUNT,
                HOT_GOODS_TTL
        );
    }
}
