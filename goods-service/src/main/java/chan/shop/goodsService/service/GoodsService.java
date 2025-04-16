package chan.shop.goodsService.service;

import chan.shop.goodsService.request.GoodsCreateRequest;
import chan.shop.goodsService.request.GoodsUpdateRequest;
import chan.shop.goodsService.response.GoodsPageResponse;
import chan.shop.goodsService.response.GoodsResponse;

import java.util.List;

public interface GoodsService {

    GoodsResponse create(GoodsCreateRequest request);
    GoodsResponse read(Long goodsId);
    GoodsResponse update(Long goodsId, GoodsUpdateRequest request);
    void delete(Long goodsId);
    GoodsPageResponse readAll(Long brandId, Long page, Long pageSize);
    List<GoodsResponse> readAllInfiniteScroll(Long goodsId, Long pageSize, Long lastGoodsId);
}
