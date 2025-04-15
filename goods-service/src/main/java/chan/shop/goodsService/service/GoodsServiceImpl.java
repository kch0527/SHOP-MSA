package chan.shop.goodsService.service;

import chan.shop.commonservice.snowflake.Snowflake;
import chan.shop.goodsService.entity.Goods;
import chan.shop.goodsService.repository.GoodsRepository;
import chan.shop.goodsService.request.GoodsCreateRequest;
import chan.shop.goodsService.request.GoodsUpdateRequest;
import chan.shop.goodsService.response.GoodsPageResponse;
import chan.shop.goodsService.response.GoodsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final Snowflake snowflake = new Snowflake();
    private final GoodsRepository goodsRepository;

    @Transactional
    public GoodsResponse create(GoodsCreateRequest request) {
        Goods goods = goodsRepository.save(
                Goods.create(snowflake.nextId(), request.getGoodsTitle(), request.getGoodsContent(), request.getPrice(), request.getQty(),
                        request.getBrandId(), request.getRegId())
        );
        return GoodsResponse.from(goods);
    }

    @Transactional
    public GoodsResponse update(Long goodsId, GoodsUpdateRequest request) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow();
        goods.update(request.getGoodsTitle(), request.getGoodsContent(), request.getPrice(), request.getQty());
        return GoodsResponse.from(goods);
    }

    public GoodsResponse read(Long goodsId) {
        return GoodsResponse.from(goodsRepository.findById(goodsId).orElseThrow());
    }

    @Transactional
    public void delete(Long goodsId) {
        goodsRepository.deleteById(goodsId);
    }

    public GoodsPageResponse readAll(Long brandId, Long page, Long pageSize) {
        return GoodsPageResponse.of(
                goodsRepository.findAll(brandId, (page - 1) * pageSize, pageSize).stream()
                        .map(GoodsResponse::from)
                        .toList(),
                goodsRepository.count(
                        brandId,
                        PageLimitCalculator.calculatorPageLimit(page, pageSize, 10L)
                )
        );
    }
}
