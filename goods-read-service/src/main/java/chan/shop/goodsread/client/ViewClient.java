package chan.shop.goodsread.client;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class ViewClient {
    private RestClient restClient;
    @Value("${endpoints.view-service.url}")
    private String viewServiceUrl;

    @PostConstruct
    public void initRestClient() {
        restClient = RestClient.create(viewServiceUrl);
    }

    // 1. redis 에서 데이터 조회
    // 2. redis에 데이터가 없었다면, count 메서드 내부 로직이 호출되면서 viewService로 원본 데이터를 요청
    // 3. redis에 데이터를 넣고 응답
    // 4. 만약 redis에 데이터가 있었다면, 그 데이터를 그대로 바로 반환
    @Cacheable(key = "#goodsId", value = "goodsViewCount")
    public long count(Long goodsId) {
        log.info("[ViewClient.count] goodsId={}", goodsId);
        try{
            return restClient.get()
                    .uri("/goods-view/goods/{goodsId}/count", goodsId)
                    .retrieve()
                    .body(Long.class);
        } catch (Exception e) {
            log.error("[ViewClient.count] goodsId={}", goodsId, e);
            return 0;
        }
    }

}
