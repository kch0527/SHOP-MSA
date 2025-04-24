package chan.shop.hotgoodsservice.service.eventhandler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.CommentDeletedEventPayload;
import chan.shop.hotgoodsservice.repository.GoodsCommentCountRepository;
import chan.shop.hotgoodsservice.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDeletedEventHandler implements EventHandler<CommentDeletedEventPayload>{
    private final GoodsCommentCountRepository goodsCommentCountRepository;

    @Override
    public void handle(Event<CommentDeletedEventPayload> event) {
        CommentDeletedEventPayload payload = event.getPayload();
        goodsCommentCountRepository.createOrUpdate(
                payload.getGoodsId(),
                payload.getGoodsCommentCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<CommentDeletedEventPayload> event) {
        return EventType.COMMENT_DELETED == event.getType();
    }

    @Override
    public Long findGoodsId(Event<CommentDeletedEventPayload> event) {
        return event.getPayload().getGoodsId();
    }
}
