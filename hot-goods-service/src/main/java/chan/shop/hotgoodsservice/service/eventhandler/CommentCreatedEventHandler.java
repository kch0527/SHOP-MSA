package chan.shop.hotgoodsservice.service.eventhandler;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.event.payload.CommentCreatedEventPayload;
import chan.shop.hotgoodsservice.repository.GoodsCommentCountRepository;
import chan.shop.hotgoodsservice.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreatedEventHandler implements EventHandler<CommentCreatedEventPayload>{
    private final GoodsCommentCountRepository goodsCommentCountRepository;

    @Override
    public void handle(Event<CommentCreatedEventPayload> event) {
        CommentCreatedEventPayload payload = event.getPayload();
        goodsCommentCountRepository.createOrUpdate(
                payload.getGoodsId(),
                payload.getGoodsCommentCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<CommentCreatedEventPayload> event) {
        return EventType.COMMENT_CREATED == event.getType();
    }

    @Override
    public Long findGoodsId(Event<CommentCreatedEventPayload> event) {
        return event.getPayload().getCommentId();
    }
}
