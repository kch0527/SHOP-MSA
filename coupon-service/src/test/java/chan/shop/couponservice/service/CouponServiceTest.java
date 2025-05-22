package chan.shop.couponservice.service;

import chan.shop.couponservice.repository.CouponCountRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

class CouponServiceTest {

    @Autowired
    CouponCountRepository couponCountRepository;

    RestClient restClient = RestClient.create("http://localhost:53622");

    @Test
    void couponTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch = new CountDownLatch(3000);

        for(int i = 0; i < 3000; i++) {
            final int userId = i;
            executorService.submit(() -> {
                CouponCreateRequest request = createCoupon((long) userId);

                restClient.post()
                        .uri("/coupon/event-coupon")
                        .body(request)
                        .retrieve()
                        .toBodilessEntity();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        Long count = restClient.get()
                .uri("/coupon/event-coupon/count")
                .retrieve()
                .body(Long.class);

        System.out.println("count = " + count);

        assertThat(count).isEqualTo(1000);
    }

    private CouponCreateRequest createCoupon(Long userId) {
        return new CouponCreateRequest(userId,20L);
    }

    @Getter
    @AllArgsConstructor
    static class CouponCreateRequest {
        private Long userId;
        private Long discountAmount;
    }

}