package chan.shop.goodsService.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class GoodsPageResponse {
    private List<GoodsResponse> goodsList;
    private Long goodsCount;

    public static GoodsPageResponse of(List<GoodsResponse> goodsList, Long goodsCount) {
        GoodsPageResponse response = new GoodsPageResponse();
        response.goodsList = goodsList;
        response.goodsCount = goodsCount;
        return response;
    }
}
