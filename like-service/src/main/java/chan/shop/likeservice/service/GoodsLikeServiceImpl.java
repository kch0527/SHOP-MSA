package chan.shop.likeservice.service;

import chan.shop.commonservice.outboxmessagerelay.OutboxEventPublisher;
import chan.shop.commonservice.snowflake.Snowflake;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsLikeEventPayload;
import chan.shop.event.payload.GoodsUnlikeEventPayload;
import chan.shop.likeservice.entity.GoodsLike;
import chan.shop.likeservice.entity.GoodsLikeCount;
import chan.shop.likeservice.repository.GoodsLikeCountRepository;
import chan.shop.likeservice.repository.GoodsLikeRepository;
import chan.shop.likeservice.response.GoodsLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoodsLikeServiceImpl implements GoodsLikeService{
    private final Snowflake snowflake = new Snowflake();
    private final GoodsLikeRepository goodsLikeRepository;
    private final GoodsLikeCountRepository goodsLikeCountRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    public GoodsLikeResponse read(Long goodsId, Long userId) {
        return goodsLikeRepository.findByGoodsIdAndUserId(goodsId, userId)
                .map(GoodsLikeResponse::from)
                .orElseThrow();
    }

    /**
     * update
     */
    @Transactional
    public void likePessimisticLock1(Long goodsId, Long userId) {
        GoodsLike goodsLike = goodsLikeRepository.save(
                GoodsLike.create(
                        snowflake.nextId(),
                        goodsId,
                        userId
                )
        );
        int result = goodsLikeCountRepository.increase(goodsId);
        if(result == 0){
            goodsLikeCountRepository.save(
                    GoodsLikeCount.init(goodsId, 1L)
            );
        }

        outboxEventPublisher.publish(
                EventType.GOODS_LIKED,
                GoodsLikeEventPayload.builder()
                        .goodsLikeId(goodsLike.getGoodsLikeId())
                        .goodsId(goodsLike.getGoodsId())
                        .userId(goodsLike.getUserId())
                        .createAt(goodsLike.getCreateAt())
                        .goodsLikeCount(count(goodsLike.getGoodsId()))
                        .build(),
                goodsLike.getGoodsId()
        );
    }

    @Transactional
    public void unlikePessimisticLock1(Long goodsId, Long userId) {
        goodsLikeRepository.findByGoodsIdAndUserId(goodsId, userId)
                .ifPresent(goodsLike -> {
                    goodsLikeRepository.delete(goodsLike);
                    goodsLikeCountRepository.decrease(goodsId);

                    outboxEventPublisher.publish(
                            EventType.GOODS_UNLIKED,
                            GoodsUnlikeEventPayload.builder()
                                    .goodsLikeId(goodsLike.getGoodsLikeId())
                                    .goodsId(goodsLike.getGoodsId())
                                    .userId(goodsLike.getUserId())
                                    .createAt(goodsLike.getCreateAt())
                                    .goodsLikeCount(count(goodsLike.getGoodsId()))
                                    .build(),
                            goodsLike.getGoodsId()
                    );
                });
    }

    /**
     * select ... for update + update
     */
    @Transactional
    public void likePessimisticLock2(Long goodsId, Long userId) {
        goodsLikeRepository.save(
                GoodsLike.create(
                        snowflake.nextId(),
                        goodsId,
                        userId
                )
        );
        GoodsLikeCount goodsLikeCount = goodsLikeCountRepository.findLockedByGoodsId(goodsId)
                .orElseGet(() -> GoodsLikeCount.init(goodsId, 0L));
        goodsLikeCount.increase();
        goodsLikeCountRepository.save(goodsLikeCount);
    }

    @Transactional
    public void unlikePessimisticLock2(Long goodsId, Long userId) {
        goodsLikeRepository.findByGoodsIdAndUserId(goodsId, userId)
                .ifPresent(goodsLike -> {
                    goodsLikeRepository.delete(goodsLike);
                    GoodsLikeCount goodsLikeCount = goodsLikeCountRepository.findLockedByGoodsId(goodsId).orElseThrow();
                    goodsLikeCount.decrease();
                });
    }

    @Transactional
    public void likeOptimisticLock(Long goodsId, Long userId) {
        goodsLikeRepository.save(
                GoodsLike.create(
                        snowflake.nextId(),
                        goodsId,
                        userId
                )
        );
        GoodsLikeCount goodsLikeCount = goodsLikeCountRepository.findById(goodsId).orElseGet(() -> GoodsLikeCount.init(goodsId, 0L));
        goodsLikeCount.increase();
        goodsLikeCountRepository.save(goodsLikeCount);
    }

    @Transactional
    public void unlikeOptimisticLock(Long goodsId, Long userId) {
        goodsLikeRepository.findByGoodsIdAndUserId(goodsId, userId)
                .ifPresent(entity -> {
                    goodsLikeRepository.delete(entity);
                    GoodsLikeCount goodsLikeCount = goodsLikeCountRepository.findById(goodsId).orElseThrow();
                    goodsLikeCount.decrease();
                });
    }

    public Long count(Long goodsId) {
        return goodsLikeCountRepository.findById(goodsId)
                .map(GoodsLikeCount::getLikeCount)
                .orElse(0L);
    }
}
