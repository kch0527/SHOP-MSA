package chan.shop.goodsread.client;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoodsClient {
    private RestClient restClient;
    @Value("${endpoints.goods-service.url}")
    private String goodsServiceUrl;

    @PostConstruct
    public void initRestClient() {
        restClient = RestClient.create(goodsServiceUrl);
    }

    public Optional<GoodsResponse> read(Long goodsId) {
        try {
            GoodsResponse goodsResponse = restClient.get()
                    .uri("/goods/{goodsId}", goodsId)
                    .retrieve()
                    .body(GoodsResponse.class);
            return Optional.ofNullable(goodsResponse);
        } catch (Exception e) {
            log.error("[GoodsClient.read] goodsId={}", goodsId, e);
            return Optional.empty();
        }
    }
    @Getter
    public static class GoodsResponse {
        private Long goodsId;
        private String goodsTitle;
        private String goodsContent;
        private Long price;
        private Long qty;
        private Long brandId;
        private Long regId;
        private LocalDateTime createAt;
        private LocalDateTime modifiedAt;
    }
}
