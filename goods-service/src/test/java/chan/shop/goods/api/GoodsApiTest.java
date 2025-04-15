package chan.shop.goods.api;

import chan.shop.goodsService.response.GoodsPageResponse;
import chan.shop.goodsService.response.GoodsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class GoodsApiTest {
    RestClient restClient = RestClient.create("http://localhost:53620");

    @Test
    void createTest() {
        GoodsResponse response = create(new GoodsCreateRequest(
                "goods3", "test", 1000L, 3L, 1L, 1L
        ));
        System.out.println("response = " + response);
    }

    @Test
    void readTest() {
        GoodsResponse response = read(168729130521841664L);
        System.out.println("response = " + response);
    }

    @Test
    void updateTest() {
        update(168729130521841664L);
        GoodsResponse response = read(168729130521841664L);
        System.out.println("response = " + response);
    }

    @Test
    void deleteTest() {
        restClient.delete().uri("/goods/{goodsId}", 168729130521841664L)
                .retrieve();
    }

    @Test
    void readAllTest() {
        GoodsPageResponse response = restClient.get()
                .uri("/goods?brandId=1&page=1&pageSize=30")
                .retrieve()
                .body(GoodsPageResponse.class);

        System.out.println("response.getGoodsCount() = " + response.getGoodsCount());
        for (GoodsResponse goods : response.getGoodsList()) {
            System.out.println("goods = " + goods);
        }
    }


    GoodsResponse create(GoodsCreateRequest request) {
        return restClient.post()
                .uri("/goods")
                .body(request)
                .retrieve()
                .body(GoodsResponse.class);
    }

    GoodsResponse read(Long goodsId) {
        return restClient.get()
                .uri("/goods/{goodsId}", goodsId)
                .retrieve()
                .body(GoodsResponse.class);
    }

    GoodsResponse update(Long goodsId) {
        return restClient.put()
                .uri("/goods/{goodsId}", goodsId)
                .body(new GoodsUpdateRequest("test1", "test", 2000L, 50L))
                .retrieve()
                .body(GoodsResponse.class);
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
    @AllArgsConstructor
    static class GoodsUpdateRequest {
        private String goodsTitle;
        private String goodsContent;
        private Long price;
        private Long qty;
    }
}
