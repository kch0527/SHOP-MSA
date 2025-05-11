package chan.shop.goodsread.service;

import chan.shop.event.Event;
import chan.shop.event.EventPayload;
import chan.shop.goodsread.client.CommentClient;
import chan.shop.goodsread.client.GoodsClient;
import chan.shop.goodsread.client.LikeClient;
import chan.shop.goodsread.client.ViewClient;
import chan.shop.goodsread.repository.BrandGoodsCountRepository;
import chan.shop.goodsread.repository.GoodsIdListRepository;
import chan.shop.goodsread.repository.GoodsQueryModel;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import chan.shop.goodsread.response.GoodsReadPageResponse;
import chan.shop.goodsread.response.GoodsReadResponse;
import chan.shop.goodsread.service.event.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoodsReadService {
    private final GoodsClient goodsClient;
    private final CommentClient commentClient;
    private final LikeClient likeClient;
    private final ViewClient viewClient;
    private final GoodsQueryModelRepository goodsQueryModelRepository;
    private final GoodsIdListRepository goodsIdListRepository;
    private final BrandGoodsCountRepository brandGoodsCountRepository;
    private final List<EventHandler> eventHandlers;

    public void handleEvent(Event<EventPayload> event) {
        for (EventHandler eventHandler : eventHandlers) {
            if (eventHandler.supports(event)) {
                eventHandler.handle(event);
            }
        }
    }

    public GoodsReadResponse read(Long goodsId) {
        GoodsQueryModel goodsQueryModel = goodsQueryModelRepository.read(goodsId)
                .or(() -> fetch(goodsId))
                .orElseThrow();

        return GoodsReadResponse.from(
                goodsQueryModel,
                viewClient.count(goodsId)
        );
    }

    private Optional<GoodsQueryModel> fetch(Long goodsId) {
        Optional<GoodsQueryModel> goodsQueryModelOptional = goodsClient.read(goodsId)
                .map(goods -> GoodsQueryModel.create(
                        goods,
                        commentClient.count(goodsId),
                        likeClient.count(goodsId)
                ));
        goodsQueryModelOptional.ifPresent(goodsQueryModel -> goodsQueryModelRepository.create(goodsQueryModel, Duration.ofDays(1)));

        log.info("[GoodsReadService.fetch] fetch data. goodsId={}, isPresent={}", goodsId, goodsQueryModelOptional.isPresent());
        return goodsQueryModelOptional;
    }

    public GoodsReadPageResponse readAll(Long brandId, Long page, Long pageSize) {
        return GoodsReadPageResponse.of(
                readAll(readAllGoodsIds(brandId, page, pageSize)),
                count(brandId)
        );
    }

    private List<GoodsReadResponse> readAll(List<Long> goodsIds) {
        Map<Long, GoodsQueryModel> goodsQueryModelMap = goodsQueryModelRepository.readAll(goodsIds);
        return goodsIds.stream()
                .map(goodsId -> goodsQueryModelMap.containsKey(goodsId) ?
                        goodsQueryModelMap.get(goodsId) :
                        fetch(goodsId).orElse(null))
                .filter(Objects::nonNull)
                .map(goodsQueryModel ->
                        GoodsReadResponse.from(
                                goodsQueryModel,
                                viewClient.count(goodsQueryModel.getGoodsId())
                        ))
                .toList();
    }

    private List<Long> readAllGoodsIds(Long brandId, Long page, Long pageSize) {
        List<Long> goodsIds = goodsIdListRepository.readAll(brandId, (page - 1) + pageSize, pageSize);
        if(pageSize == goodsIds.size()) {
            log.info("[GoodsReadService.readAllGoodsIds] return redis data.");
            return goodsIds;
        }
        log.info("[GoodsReadService.readAllGoodsIds] return origin data.");
        return goodsClient.readAll(brandId, page, pageSize).getGoodsList().stream()
                .map(GoodsClient.GoodsResponse::getGoodsId)
                .toList();
    }

    private long count(Long brandId) {
        Long result = brandGoodsCountRepository.read(brandId);
        if(result != null) {
            return result;
        }
        long count = goodsClient.count(brandId);
        brandGoodsCountRepository.createOrUpdate(brandId, count);

        return count;
    }

    public List<GoodsReadResponse> readAllInfiniteScroll(Long brandId, Long lastGoodsId, Long pageSize) {
        return readAll(
                readAllInfiniteScrollGoodsIds(brandId, lastGoodsId, pageSize)
        );
    }

    private List<Long> readAllInfiniteScrollGoodsIds(Long brandId, Long lastGoodsId, Long pageSize) {
        List<Long> goodsIds = goodsIdListRepository.readAllInfiniteScroll(brandId, lastGoodsId, pageSize);
        if(pageSize == goodsIds.size()) {
            log.info("[GoodsReadService.readAllInfiniteScrollGoodsIds] return redis data.");
            return goodsIds;
        }

        log.info("[GoodsReadService.readAllInfiniteScrollGoodsIds] return origin data.");
        return goodsClient.readAllInfiniteScroll(brandId, lastGoodsId, pageSize).stream()
                .map(GoodsClient.GoodsResponse::getGoodsId)
                .toList();
    }
}
