package chan.shop.hotgoodsservice.api;

import chan.shop.hotgoodsservice.response.HotGoodsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class HotGoodsApiTest {
    RestClient restClient = RestClient.create("http://localhost:65281");

    @Test
    void readAllTest() {
        List<HotGoodsResponse> responses = restClient.get()
                .uri("hot-goods/goods/date/{dateStr}", "20250429")
                .retrieve()
                .body(new ParameterizedTypeReference<List<HotGoodsResponse>>() {
                });

        for (HotGoodsResponse response : responses) {
            System.out.println("response : " + response);
        }
    }
}
