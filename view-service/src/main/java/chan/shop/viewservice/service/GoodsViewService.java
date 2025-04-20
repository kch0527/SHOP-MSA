package chan.shop.viewservice.service;

public interface GoodsViewService {
    Long increase(Long goodsId, Long userId);
    Long count(Long goodsId);
}
