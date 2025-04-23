package chan.shop.hotgoodsservice.client;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoodsClient {
    private RestClient restClient;

    @Value("${endpoints.goods-service.url}")
    private String goodsServiceUrl;

    @PostConstruct
    void initRestClient() {
        restClient = RestClient.create(goodsServiceUrl);
    }

    public GoodsResponse read(Long goodsId) {
        try {
            return restClient.get()
                    .uri("/goods/{goodsId}", goodsId)
                    .retrieve()
                    .body(GoodsResponse.class);
        } catch (Exception e) {
            log.error("[GoodsClient.read] goodsId={}", goodsId, e);
        }
        return null;
    }

    @Getter
    public static class GoodsResponse {
        private Long goodsId;
        private String goodsTitle;
        private LocalDateTime createAt;
    }
}
