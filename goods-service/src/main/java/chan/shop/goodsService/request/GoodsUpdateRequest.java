package chan.shop.goodsService.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GoodsUpdateRequest {
    private String goodsTitle;
    private String goodsContent;
    private Long price;
    private Long qty;
}
