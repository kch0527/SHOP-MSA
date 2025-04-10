package chan.shop.goodsService.response;

import chan.shop.goodsService.entity.Goods;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class GoodsResponse {
    private Long goodsId;
    private String goodsTitle;
    private String goodsContent;
    private Long price;
    private Long qty;
    private Long brandId;
    private Long regId;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static GoodsResponse from(Goods goods) {
        GoodsResponse response = new GoodsResponse();
        response.goodsId = goods.getGoodsId();
        response.goodsTitle = goods.getGoodsTitle();
        response.goodsContent = goods.getGoodsContent();
        response.price = goods.getPrice();
        response.qty = goods.getQty();
        response.brandId = goods.getBrandId();
        response.regId = goods.getRegId();
        response.createAt = goods.getCreateAt();
        response.modifiedAt = goods.getModifiedAt();
        return response;
    }
}
