package chan.shop.goodsread.response;

import chan.shop.goodsread.repository.GoodsQueryModel;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class GoodsReadResponse {
    private Long goodsId;
    private String goodsTitle;
    private String goodsContent;
    private Long price;
    private Long qty;
    private Long brandId;
    private Long regId;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    private Long goodsCommentCount;
    private Long goodsLikeCount;
    private Long goodsViewCount;

    public static GoodsReadResponse form(GoodsQueryModel goodsQueryModel, Long viewCount) {
        GoodsReadResponse response = new GoodsReadResponse();
        response.goodsId = goodsQueryModel.getGoodsId();
        response.goodsTitle = goodsQueryModel.getGoodsTitle();
        response.goodsContent = goodsQueryModel.getGoodsContent();
        response.price = goodsQueryModel.getPrice();
        response.qty = goodsQueryModel.getQty();
        response.brandId = goodsQueryModel.getBrandId();
        response.regId = goodsQueryModel.getRegId();
        response.createAt = goodsQueryModel.getCreateAt();
        response.modifiedAt = goodsQueryModel.getModifiedAt();
        response.goodsCommentCount = goodsQueryModel.getGoodsCommentCount();
        response.goodsLikeCount = goodsQueryModel.getGoodsLikeCount();
        response.goodsViewCount = viewCount;
        return response;
    }
}
