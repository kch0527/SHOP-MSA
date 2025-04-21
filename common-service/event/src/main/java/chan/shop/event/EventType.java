package chan.shop.event;

import chan.shop.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    GOODS_CREATED(GoodsCreatedEventPayload.class, Topic.CHAN_SHOP_GOODS),
    GOODS_UPDATED(GoodsUpdatedEventPayload.class, Topic.CHAN_SHOP_GOODS),
    GOODS_DELETED(GoodsDeletedEventPayload.class, Topic.CHAN_SHOP_GOODS),
    COMMENT_CREATED(CommentCreatedEventPayload.class, Topic.CHAN_SHOP_COMMENT),
    COMMENT_DELETED(CommentDeletedEventPayload.class, Topic.CHAN_SHOP_COMMENT),
    GOODS_LIKED(GoodsLikeEventPayload.class, Topic.CHAN_SHOP_LIKE),
    GOODS_UNLIKED(GoodsUnlikeEventPayload.class, Topic.CHAN_SHOP_LIKE),
    GOODS_VIEWED(GoodsViewEventPayload.class, Topic.CHAN_SHOP_VIEW);

    private final Class<? extends EventPayload> payloadClass; // event 들이 어떤 payload 갖는지
    private final String topic; // event 들이 어떤 topic 으로 전달될 수 있는지

    public static EventType from(String type) {
        try{
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String CHAN_SHOP_GOODS = "chan-shop-goods";
        public static final String CHAN_SHOP_COMMENT = "chan-shop-comment";
        public static final String CHAN_SHOP_LIKE = "chan-shop-like";
        public static final String CHAN_SHOP_VIEW = "chan-shop-view";
    }
}
