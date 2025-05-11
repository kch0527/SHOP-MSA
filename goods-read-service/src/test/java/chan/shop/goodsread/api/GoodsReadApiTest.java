package chan.shop.goodsread.api;

import chan.shop.goodsread.response.GoodsReadPageResponse;
import chan.shop.goodsread.response.GoodsReadResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class GoodsReadApiTest {
    RestClient goodsReadrestClient = RestClient.create("http://localhost:64314");
    RestClient goodsRestClient = RestClient.create("http://localhost:64221");

    @Test
    void readTest() {
        GoodsReadResponse response = goodsReadrestClient.get()
                .uri("/goods/{goodsId}", 178842667145453568L)
                .retrieve()
                .body(GoodsReadResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void readAllTest() {
        GoodsReadPageResponse response1 = goodsReadrestClient.get()
                .uri("/goods?brandId=%s&page=%s&pageSize=%s".formatted(1L, 3000L, 5))
                .retrieve()
                .body(GoodsReadPageResponse.class);

        System.out.println("response1.getGoodsCount() = " + response1.getGoodsCount());
        for (GoodsReadResponse goods : response1.getGoodsList()) {
            System.out.println("goods.getGoodsId() = " + goods.getGoodsId());
        }

        GoodsReadPageResponse response2 = goodsRestClient.get()
                .uri("/goods?brandId=%s&page=%s&pageSize=%s".formatted(1L, 3000L, 5))
                .retrieve()
                .body(GoodsReadPageResponse.class);

        System.out.println("response2.getGoodsCount() = " + response2.getGoodsCount());
        for (GoodsReadResponse goods : response2.getGoodsList()) {
            System.out.println("goods.getGoodsId() = " + goods.getGoodsId());
        }
    }

    @Test
    void readAllInfiniteScrollTest() {
        List<GoodsReadResponse> response1 = goodsReadrestClient.get()
                .uri("goods/infinite-scroll?brandId=%s&pageSize=%s&lastGoodsId=%s".formatted(1L, 5L, 179822706951426048L))
                .retrieve()
                .body(new ParameterizedTypeReference<List<GoodsReadResponse>>() {
                });

        for (GoodsReadResponse response : response1) {
            System.out.println("response = " + response.getGoodsId());
        }

        List<GoodsReadResponse> response2 = goodsRestClient.get()
                .uri("goods/infinite-scroll?brandId=%s&pageSize=%s&lastGoodsId=%s".formatted(1L, 5L, 179822706951426048L))
                .retrieve()
                .body(new ParameterizedTypeReference<List<GoodsReadResponse>>() {
                });

        for (GoodsReadResponse response : response2) {
            System.out.println("response = " + response.getGoodsId());
        }
    }
}
