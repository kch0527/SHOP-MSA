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
public class CommentClient {
    private RestClient restClient;
    @Value("${endpoints.comment-service.url}")
    private String commentServiceUrl;

    @PostConstruct
    public void initRestClient() {
        restClient = RestClient.create(commentServiceUrl);
    }

    public long count(Long goodsId) {
        try{
            return restClient.get()
                    .uri("/v2/comments/goods/{goodsId}/count", goodsId)
                    .retrieve()
                    .body(Long.class);
        } catch (Exception e) {
            log.error("[CommentClient.count] goodsId={}", goodsId, e);
            return 0;
        }
    }

}
