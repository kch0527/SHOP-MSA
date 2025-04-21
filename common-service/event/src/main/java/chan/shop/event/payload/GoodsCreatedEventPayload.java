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
public class GoodsCreatedEventPayload implements EventPayload {
    private Long goodsId;
    private String goodsTitle;
    private String goodsContent;
    private Long price;
    private Long qty;
    private Long brandId;
    private Long regId;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long brandGoodsCount;
}
