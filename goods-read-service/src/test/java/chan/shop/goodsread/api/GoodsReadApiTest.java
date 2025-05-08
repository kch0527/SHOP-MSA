package chan.shop.goodsread.api;

import chan.shop.goodsread.response.GoodsReadResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class GoodsReadApiTest {
    RestClient restClient = RestClient.create("http://localhost:62635");

    @Test
    void readTest() {
        GoodsReadResponse response = restClient.get()
                .uri("/goods/{goodsId}", 178842667145453568L)
                .retrieve()
                .body(GoodsReadResponse.class);

        System.out.println("response = " + response);
    }
}
