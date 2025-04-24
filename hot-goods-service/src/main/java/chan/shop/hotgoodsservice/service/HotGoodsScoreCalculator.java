package chan.shop.hotgoodsservice.service;

import chan.shop.hotgoodsservice.repository.GoodsCommentCountRepository;
import chan.shop.hotgoodsservice.repository.GoodsLikeCountRepository;
import chan.shop.hotgoodsservice.repository.GoodsViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotGoodsScoreCalculator {
    private final GoodsLikeCountRepository goodsLikeCountRepository;
    private final GoodsViewCountRepository goodsViewCountRepository;
    private final GoodsCommentCountRepository goodsCommentCountRepository;

    private static final long GOODS_LIKE_COUNT_WEIGHT = 3;
    private static final long GOODS_VIEW_COUNT_WEIGHT = 3;
    private static final long GOODS_COMMENT_COUNT_WEIGHT = 1;

    public long calculate(Long goodsId) {
        Long goodsLikeCount = goodsLikeCountRepository.read(goodsId);
        Long goodsViewCount = goodsViewCountRepository.read(goodsId);
        Long goodsCommentCount = goodsCommentCountRepository.read(goodsId);

        return goodsLikeCount * GOODS_LIKE_COUNT_WEIGHT
                + goodsViewCount * GOODS_VIEW_COUNT_WEIGHT
                + goodsCommentCount * GOODS_COMMENT_COUNT_WEIGHT;
    }
}
