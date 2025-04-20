package chan.shop.likeservice.service;

import chan.shop.likeservice.response.GoodsLikeResponse;

public interface GoodsLikeService {
    GoodsLikeResponse read(Long goodsId, Long userId);
    void like(Long goodsId, Long userId);
    void unlike(Long goodsId, Long userId);
}
