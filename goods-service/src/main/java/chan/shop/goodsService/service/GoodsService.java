package chan.shop.goodsService.service;

import chan.shop.goodsService.request.GoodsCreateRequest;
import chan.shop.goodsService.request.GoodsUpdateRequest;
import chan.shop.goodsService.response.GoodsPageResponse;
import chan.shop.goodsService.response.GoodsResponse;

public interface GoodsService {

    public GoodsResponse create(GoodsCreateRequest request);
    public GoodsResponse read(Long goodsId);
    public GoodsResponse update(Long goodsId, GoodsUpdateRequest request);
    public void delete(Long goodsId);
    public GoodsPageResponse readAll(Long brandId, Long page, Long pageSize);
}
