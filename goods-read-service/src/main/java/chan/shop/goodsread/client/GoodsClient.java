package chan.shop.goodsread.client;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
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

    public GoodsPageResponse readAll(Long brandId, Long page, Long pageSize) {
        try{
            return restClient.get()
                    .uri("goods?brandId=%s&page=%s&pageSize=%s".formatted(brandId,page, pageSize))
                    .retrieve()
                    .body(GoodsPageResponse.class);
        } catch (Exception e) {
            log.error("[GoodsClient.readAll] brandId={}, page={}, pageSize={}", brandId, page, pageSize, e);
            return GoodsPageResponse.EMPTY;
        }
    }

    public List<GoodsResponse> readAllInfiniteScroll(Long brandId, Long lastGoodsId, Long pageSize) {
        try{
            return restClient.get()
                    .uri(lastGoodsId != null ?
                            "/goods/infinite-scroll?brandId=%s&lastGoodsId=%s&pageSize=%s".formatted(brandId, lastGoodsId, pageSize) :
                            "/goods/infinite-scroll?brandId=%s&pageSize=%s".formatted(brandId, pageSize))
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<GoodsResponse>>() {
                    });
        } catch (Exception e) {
            log.error("[GoodsClient.readAllInfiniteScroll] brandId={}, lastGoodsId={}, pageSize={}", brandId, lastGoodsId, pageSize, e);
            return List.of();
        }
    }

    public long count(Long brandId) {
        try {
            return restClient.get()
                    .uri("/goods/brand/{brandId}/count", brandId)
                    .retrieve()
                    .body(Long.class);
        } catch (Exception e) {
            log.error("[GoodsClient.count] brandId={}", brandId, e);
            return 0;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoodsPageResponse {
        private List<GoodsResponse> goodsList;
        private Long goodsCount;

        public static GoodsPageResponse EMPTY = new GoodsPageResponse(List.of(), 0L);
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
