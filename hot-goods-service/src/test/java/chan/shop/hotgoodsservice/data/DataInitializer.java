package chan.shop.hotgoodsservice.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.random.RandomGenerator;

public class DataInitializer {
    RestClient goodsRestClient = RestClient.create("http://localhost:62180");
    RestClient likeRestClient = RestClient.create("http://localhost:62212");
    RestClient viewRestClient = RestClient.create("http://localhost:62235");

    @Test
    void initialize() {
        for (int i = 0; i < 30; i++) {
            Long goodsId = createGoods();
            long likeCount = RandomGenerator.getDefault().nextLong(100);
            long viewCount = RandomGenerator.getDefault().nextLong(200);

            like(goodsId, likeCount);
            view(goodsId, viewCount);
        }
    }

    Long createGoods() {
        return goodsRestClient.post()
                .uri("/goods")
                .body(new GoodsCreateRequest("test1", "test11", 50000L, 10L, 1L, 1L))
                .retrieve()
                .body(GoodsResponse.class)
                .getGoodsId();
    }

    @Getter
    @AllArgsConstructor
    static class GoodsCreateRequest {
        private String goodsTitle;
        private String goodsContent;
        private Long price;
        private Long qty;
        private Long brandId;
        private Long regId;
    }

    @Getter
    static class GoodsResponse {
        private Long goodsId;
    }

    void like(Long goodsId, long likeCount) {
        while (likeCount-- > 0) {
            likeRestClient.post()
                    .uri("/goods-like/goods/{goodsId}/user/{userId}/pessimistic-lock-1", goodsId, likeCount)
                    .retrieve()
                    .toBodilessEntity();
        }
    }

    void view(Long goodsId, long viewCount) {
        while (viewCount-- > 0) {
            viewRestClient.post()
                    .uri("/goods-view/goods/{goodsId}/user/{userId}", goodsId, viewCount)
                    .retrieve()
                    .toBodilessEntity();
        }
    }
}
