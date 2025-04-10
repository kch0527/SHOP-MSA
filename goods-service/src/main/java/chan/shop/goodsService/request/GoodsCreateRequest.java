package chan.shop.goodsService.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GoodsCreateRequest {
    private String goodsTitle;
    private String goodsContent;
    private Long price;
    private Long qty;
    private Long brandId;
    private Long regId;
}
