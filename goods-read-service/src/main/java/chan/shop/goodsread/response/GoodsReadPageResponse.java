package chan.shop.goodsread.response;

import lombok.Getter;

import java.util.List;

@Getter
public class GoodsReadPageResponse {
    private List<GoodsReadResponse> goodsList;
    private Long goodsCount;

    public static GoodsReadPageResponse of(List<GoodsReadResponse> goodsList, Long goodsCount) {
        GoodsReadPageResponse response = new GoodsReadPageResponse();
        response.goodsList = goodsList;
        response.goodsCount = goodsCount;
        return response;
    }
}
