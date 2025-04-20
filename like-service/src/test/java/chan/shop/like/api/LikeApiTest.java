package chan.shop.like.api;

import chan.shop.likeservice.response.GoodsLikeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class LikeApiTest {
    RestClient restClient = RestClient.create("http://localhost:57342");

    @Test
    void likeAndUnlikeTest() {
        Long goodsId = 1L;

        like(goodsId, 1L);
        like(goodsId, 2L);
        like(goodsId, 3L);

        GoodsLikeResponse response1 = read(goodsId, 1L);
        GoodsLikeResponse response2 = read(goodsId, 2L);
        GoodsLikeResponse response3 = read(goodsId, 3L);

        System.out.println("response1 : " + response1);
        System.out.println("response2 : " + response2);
        System.out.println("response3 : " + response3);

        unlike(goodsId, 1L);
        unlike(goodsId, 2L);
        unlike(goodsId, 3L);

    }

    void like(Long goodsId, Long userId) {
        restClient.post()
                .uri("/goods-like/goods/{goodsId}/user/{userId}", goodsId, userId)
                .retrieve()
                .toBodilessEntity();
    }

    void unlike(Long goodsId, Long userId) {
        restClient.delete()
                .uri("/goods-like/goods/{goodsId}/user/{userId}", goodsId, userId)
                .retrieve()
                .toBodilessEntity();
    }

    GoodsLikeResponse read(Long goodsId, Long userId) {
        return restClient.get()
                .uri("/goods-like/goods/{goodsId}/user/{userId}", goodsId, userId)
                .retrieve()
                .body(GoodsLikeResponse.class);
    }
}
