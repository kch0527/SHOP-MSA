package chan.shop.event.payload;

import chan.shop.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsUnlikeEventPayload implements EventPayload {
    private Long goodsLikeId;
    private Long goodsId;
    private Long userId;
    private LocalDateTime createAt;
    private Long goodsLikeCount;
}
