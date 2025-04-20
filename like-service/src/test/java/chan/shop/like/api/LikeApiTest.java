package chan.shop.like.api;

import chan.shop.likeservice.response.GoodsLikeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LikeApiTest {
    RestClient restClient = RestClient.create("http://localhost:54093");

    @Test
    void likeAndUnlikeTest() {
        Long goodsId = 1L;

        like(goodsId, 1L, "pessimistic-lock-1");
        like(goodsId, 2L, "pessimistic-lock-1");
        like(goodsId, 3L, "pessimistic-lock-1");

        GoodsLikeResponse response1 = read(goodsId, 1L);
        GoodsLikeResponse response2 = read(goodsId, 2L);
        GoodsLikeResponse response3 = read(goodsId, 3L);

        System.out.println("response1 : " + response1);
        System.out.println("response2 : " + response2);
        System.out.println("response3 : " + response3);

        unlike(goodsId, 1L);
        unlike(goodsId, 2L);
        unlike(goodsId, 3L);

    }

    void like(Long goodsId, Long userId, String lockType) {
        restClient.post()
                .uri("/goods-like/goods/{goodsId}/user/{userId}/" + lockType, goodsId, userId)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    return;
                })
                .toBodilessEntity();
    }

    void unlike(Long goodsId, Long userId) {
        restClient.delete()
                .uri("/goods-like/goods/{goodsId}/user/{userId}", goodsId, userId)
                .retrieve()
                .toBodilessEntity();
    }

    GoodsLikeResponse read(Long goodsId, Long userId) {
        return restClient.get()
                .uri("/goods-like/goods/{goodsId}/user/{userId}", goodsId, userId)
                .retrieve()
                .body(GoodsLikeResponse.class);
    }

    @Test
    void likePerformanceTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        likePerformanceTest(executorService, 1111L, "pessimistic-lock-1");
        likePerformanceTest(executorService, 2222L, "pessimistic-lock-2");
        likePerformanceTest(executorService, 3333L, "optimistic-lock");
    }

    void likePerformanceTest(ExecutorService executorService, Long goodsId, String lockType) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(3000);
        System.out.println(lockType + " start");

        like(goodsId, 1L, lockType);

        long start = System.nanoTime();

        for (int i = 0; i < 3000; i++) {
            long userId = i + 2;
            executorService.submit(() -> {
                like(goodsId, userId, lockType);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        long end = System.nanoTime();

        System.out.println("lockType : " + lockType + ", time : " + (end - start) / 1000000 + "ms");
        System.out.println(lockType + " end");

        Long count = restClient.get()
                .uri("/goods-like/goods/{goodsId}/count", goodsId)
                .retrieve()
                .body(Long.class);

        System.out.println("count : " + count);
    }
}
