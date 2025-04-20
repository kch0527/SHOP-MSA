package chan.shop.likeservice.service;

import chan.shop.likeservice.response.GoodsLikeResponse;

public interface GoodsLikeService {
    GoodsLikeResponse read(Long goodsId, Long userId);
    void likePessimisticLock1(Long goodsId, Long userId);
    void unlikePessimisticLock1(Long goodsId, Long userId);
    void likePessimisticLock2(Long goodsId, Long userId);
    void unlikePessimisticLock2(Long goodsId, Long userId);
    void likeOptimisticLock(Long goodsId, Long userId);
    void unlikeOptimisticLock(Long goodsId, Long userId);
    Long count(Long goodsId);
}
