package chan.shop.commonservice.outboxmessagerelay;

import chan.shop.commonservice.snowflake.Snowflake;
import chan.shop.event.Event;
import chan.shop.event.EventPayload;
import chan.shop.event.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {
    private final Snowflake outboxIdSnowflake = new Snowflake();
    private final Snowflake eventIdSnowflake = new Snowflake();
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(EventType type, EventPayload payload, Long shardKey) {
        // goodsId = 10, shardKey == goodsId
        // 10 % 4 = 2 (물리적 샤드)
        Outbox outbox = Outbox.create(
                outboxIdSnowflake.nextId(),
                type,
                Event.of(
                        eventIdSnowflake.nextId(), type, payload
                ).toJson(),
                shardKey % MessageRelayConstants.SHARD_COUNT
        );
        applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
    }

}
