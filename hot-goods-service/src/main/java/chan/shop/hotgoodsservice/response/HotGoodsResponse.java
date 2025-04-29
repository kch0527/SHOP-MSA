package chan.shop.hotgoodsservice.response;

import chan.shop.hotgoodsservice.client.GoodsClient;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class HotGoodsResponse {
    private Long goodsId;
    private String goodsTitle;
    private LocalDateTime createAt;

    public static HotGoodsResponse from(GoodsClient.GoodsResponse goodsResponse) {
        HotGoodsResponse response = new HotGoodsResponse();
        response.goodsId = goodsResponse.getGoodsId();
        response.goodsTitle = goodsResponse.getGoodsTitle();
        response.createAt = goodsResponse.getCreateAt();
        return response;
    }
}
