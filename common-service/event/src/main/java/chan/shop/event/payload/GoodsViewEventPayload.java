package chan.shop.event.payload;

import chan.shop.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsViewEventPayload implements EventPayload {
    private Long goodsId;
    private Long goodsViewCount;
}
