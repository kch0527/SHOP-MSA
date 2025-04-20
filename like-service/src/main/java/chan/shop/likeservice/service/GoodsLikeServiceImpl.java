package chan.shop.likeservice.service;

import chan.shop.commonservice.snowflake.Snowflake;
import chan.shop.likeservice.entity.GoodsLike;
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

    public GoodsLikeResponse read(Long goodsId, Long userId) {
        return goodsLikeRepository.findByGoodsIdAndUserId(goodsId, userId)
                .map(GoodsLikeResponse::from)
                .orElseThrow();
    }

    @Transactional
    public void like(Long goodsId, Long userId) {
        goodsLikeRepository.save(
                GoodsLike.create(
                        snowflake.nextId(),
                        goodsId,
                        userId
                )
        );
    }

    @Transactional
    public void unlike(Long goodsId, Long userId) {
        goodsLikeRepository.findByGoodsIdAndUserId(goodsId, userId)
                .ifPresent(goodsLikeRepository::delete);
    }
}
