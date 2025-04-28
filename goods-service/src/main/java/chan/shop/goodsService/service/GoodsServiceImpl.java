package chan.shop.goodsService.service;

import chan.shop.commonservice.outboxmessagerelay.OutboxEventPublisher;
import chan.shop.commonservice.snowflake.Snowflake;
import chan.shop.event.EventType;
import chan.shop.event.payload.GoodsCreatedEventPayload;
import chan.shop.event.payload.GoodsDeletedEventPayload;
import chan.shop.event.payload.GoodsUpdatedEventPayload;
import chan.shop.goodsService.entity.BrandGoodsCount;
import chan.shop.goodsService.entity.Goods;
import chan.shop.goodsService.repository.BrandGoodsCountRepository;
import chan.shop.goodsService.repository.GoodsRepository;
import chan.shop.goodsService.request.GoodsCreateRequest;
import chan.shop.goodsService.request.GoodsUpdateRequest;
import chan.shop.goodsService.response.GoodsPageResponse;
import chan.shop.goodsService.response.GoodsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final Snowflake snowflake = new Snowflake();
    private final GoodsRepository goodsRepository;
    private final BrandGoodsCountRepository brandGoodsCountRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public GoodsResponse create(GoodsCreateRequest request) {
        Goods goods = goodsRepository.save(
                Goods.create(snowflake.nextId(), request.getGoodsTitle(), request.getGoodsContent(), request.getPrice(), request.getQty(),
                        request.getBrandId(), request.getRegId())
        );
        int result = brandGoodsCountRepository.increase(request.getBrandId());
        if(result == 0) {
            brandGoodsCountRepository.save(
                    BrandGoodsCount.init(request.getBrandId(), 1L)
            );
        }

        outboxEventPublisher.publish(
                EventType.GOODS_CREATED,
                GoodsCreatedEventPayload.builder()
                        .goodsId(goods.getGoodsId())
                        .goodsTitle(goods.getGoodsTitle())
                        .goodsContent(goods.getGoodsContent())
                        .price(goods.getPrice())
                        .qty(goods.getQty())
                        .brandId(goods.getBrandId())
                        .regId(goods.getRegId())
                        .createAt(goods.getCreateAt())
                        .modifiedAt(goods.getModifiedAt())
                        .brandGoodsCount(count(goods.getBrandId()))
                        .build(),
                goods.getBrandId()
        );

        return GoodsResponse.from(goods);
    }

    @Transactional
    public GoodsResponse update(Long goodsId, GoodsUpdateRequest request) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow();
        goods.update(request.getGoodsTitle(), request.getGoodsContent(), request.getPrice(), request.getQty());

        outboxEventPublisher.publish(
                EventType.GOODS_UPDATED,
                GoodsUpdatedEventPayload.builder()
                        .goodsId(goods.getGoodsId())
                        .goodsTitle(goods.getGoodsTitle())
                        .goodsContent(goods.getGoodsContent())
                        .price(goods.getPrice())
                        .qty(goods.getQty())
                        .brandId(goods.getBrandId())
                        .regId(goods.getRegId())
                        .createAt(goods.getCreateAt())
                        .modifiedAt(goods.getModifiedAt())
                        .build(),
                goods.getBrandId()
        );

        return GoodsResponse.from(goods);
    }

    public GoodsResponse read(Long goodsId) {
        return GoodsResponse.from(goodsRepository.findById(goodsId).orElseThrow());
    }

    @Transactional
    public void delete(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow();
        goodsRepository.delete(goods);
        brandGoodsCountRepository.decrease(goods.getBrandId());

        outboxEventPublisher.publish(
                EventType.GOODS_DELETED,
                GoodsDeletedEventPayload.builder()
                        .goodsId(goods.getGoodsId())
                        .goodsTitle(goods.getGoodsTitle())
                        .goodsContent(goods.getGoodsContent())
                        .price(goods.getPrice())
                        .qty(goods.getQty())
                        .brandId(goods.getBrandId())
                        .regId(goods.getRegId())
                        .createAt(goods.getCreateAt())
                        .modifiedAt(goods.getModifiedAt())
                        .brandGoodsCount(count(goods.getBrandId()))
                        .build(),
                goods.getBrandId()
        );
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

    public List<GoodsResponse> readAllInfiniteScroll(Long goodsId, Long pageSize, Long lastGoodsId) {
        List<Goods> goods = lastGoodsId == null ?
                goodsRepository.findAllInfiniteScroll(goodsId, pageSize) :
                goodsRepository.findAllInfiniteScroll(goodsId, pageSize, lastGoodsId);
        return goods.stream().map(GoodsResponse::from).toList();
    }

    public Long count(Long brandId) {
        return brandGoodsCountRepository.findById(brandId)
                .map(BrandGoodsCount::getGoodsCount)
                .orElse(0L);
    }
}
