package chan.shop.goodsread.client;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeClient {
    private RestClient restClient;
    @Value("${endpoints.like-service.url}")
    private String likeServiceUrl;

    @PostConstruct
    public void initRestClient() {
        restClient = RestClient.create(likeServiceUrl);
    }

    public long count(Long goodsId) {
        try{
            return restClient.get()
                    .uri("/goods-like/goods/{goodsId}/count", goodsId)
                    .retrieve()
                    .body(Long.class);
        } catch (Exception e) {
            log.error("[LikeClient.count] goodsId={}", goodsId, e);
            return 0;
        }
    }

}
