package chan.shop.goodsread.service.event.handler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.CommentCreatedEventPayload;
import chan.shop.event.payload.CommentDeletedEventPayload;
import chan.shop.goodsread.repository.GoodsQueryModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDeleteEventHandler implements EventHandler<CommentDeletedEventPayload>{
    private final GoodsQueryModelRepository goodsQueryModelRepository;

    @Override
    public void handle(Event<CommentDeletedEventPayload> event) {
        goodsQueryModelRepository.read(event.getPayload().getGoodsId())
                .ifPresent(goodsQueryModel -> {
                    goodsQueryModel.update(event.getPayload());
                    goodsQueryModelRepository.update(goodsQueryModel);
                });
    }

    @Override
    public boolean supports(Event<CommentDeletedEventPayload> event) {
        return EventType.COMMENT_DELETED == event.getType();
    }
}
