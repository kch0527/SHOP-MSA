package chan.shop.goodsread.service.event.handler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.CommentCreatedEventPayload;
import chan.shop.event.payload.GoodsUpdatedEventPayload;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreateEventHandler implements EventHandler<CommentCreatedEventPayload>{
    private final GoodsQueryModelRepository goodsQueryModelRepository;

    @Override
    public void handle(Event<CommentCreatedEventPayload> event) {
        goodsQueryModelRepository.read(event.getPayload().getGoodsId())
                .ifPresent(goodsQueryModel -> {
                    goodsQueryModel.update(event.getPayload());
                    goodsQueryModelRepository.update(goodsQueryModel);
                });
    }

    @Override
    public boolean supports(Event<CommentCreatedEventPayload> event) {
        return EventType.COMMENT_CREATED == event.getType();
    }
}
