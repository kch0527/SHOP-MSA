package chan.shop.event;

import chan.shop.event.payload.GoodsCreatedEventPayload;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {
    @Test
    void serde() {
        // given
        GoodsCreatedEventPayload payload = GoodsCreatedEventPayload.builder()
                .goodsId(1L)
                .goodsTitle("test")
                .goodsContent("test")
                .price(10000L)
                .qty(30L)
                .brandId(1L)
                .regId(1L)
                .createAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .brandGoodsCount(50L)
                .build();

        Event<EventPayload> event = Event.of(
                1234L,
                EventType.GOODS_CREATED,
                payload
        );

        String json = event.toJson();
        System.out.println("json : " + json);

        // when
        Event<EventPayload> result = Event.fromJson(json);

        // then
        assertThat(result.getEventId()).isEqualTo(event.getEventId());
        assertThat(result.getType()).isEqualTo(event.getType());
        assertThat(result.getPayLoad()).isInstanceOf(payload.getClass());

        GoodsCreatedEventPayload resultPayload = (GoodsCreatedEventPayload) result.getPayLoad();
        assertThat(resultPayload.getGoodsId()).isEqualTo(payload.getGoodsId());
        assertThat(resultPayload.getGoodsTitle()).isEqualTo(payload.getGoodsTitle());
        assertThat(resultPayload.getCreateAt()).isEqualTo(payload.getCreateAt());
    }
}
