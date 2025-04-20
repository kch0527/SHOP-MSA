package chan.shop.likeservice.response;

import chan.shop.likeservice.entity.GoodsLike;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class GoodsLikeResponse {
    private Long goodsLikeId;
    private Long goodsId;
    private Long userId;
    private LocalDateTime createAt;

    public static GoodsLikeResponse from(GoodsLike goodsLike) {
        GoodsLikeResponse response = new GoodsLikeResponse();
        response.goodsLikeId = goodsLike.getGoodsLikeId();
        response.goodsId = goodsLike.getGoodsId();
        response.userId = goodsLike.getUserId();
        response.createAt = goodsLike.getCreateAt();
        return response;
    }
}
