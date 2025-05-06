package chan.shop.goodsread.service;

import chan.shop.event.Event;
import chan.shop.event.EventPayload;
import chan.shop.goodsread.client.CommentClient;
import chan.shop.goodsread.client.GoodsClient;
import chan.shop.goodsread.client.LikeClient;
import chan.shop.goodsread.client.ViewClient;
import chan.shop.goodsread.repository.GoodsQueryModel;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import chan.shop.goodsread.response.GoodsReadResponse;
import chan.shop.goodsread.service.event.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
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

        return GoodsReadResponse.form(
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
}
