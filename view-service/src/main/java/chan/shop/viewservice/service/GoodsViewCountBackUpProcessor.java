package chan.shop.viewservice.service;

import chan.shop.commonservice.outboxmessagerelay.OutboxEventPublisher;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsViewEventPayload;
import chan.shop.viewservice.entity.GoodsViewCount;
import chan.shop.viewservice.repository.GoodsViewCountBackUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GoodsViewCountBackUpProcessor {
    private final GoodsViewCountBackUpRepository goodsViewCountBackUpRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public void backUp(Long goodsId, Long viewCount) {
        int result = goodsViewCountBackUpRepository.updateViewCount(goodsId, viewCount);
        if (result == 0) {
            goodsViewCountBackUpRepository.findById(goodsId)
                    .ifPresentOrElse(ignored -> { },
                            () -> goodsViewCountBackUpRepository.save(
                                    GoodsViewCount.init(goodsId, viewCount)
                            ));
        }

        outboxEventPublisher.publish(
                EventType.GOODS_VIEWED,
                GoodsViewEventPayload.builder()
                        .goodsId(goodsId)
                        .goodsViewCount(viewCount)
                        .build(),
                goodsId
        );
    }
}
