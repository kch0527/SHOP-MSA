package chan.shop.couponservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponCountRepository {
    private final StringRedisTemplate redisTemplate;

    // event::coupon_count
    private static final String KEY_FORMAT = "event::coupon_count";

    private String generateKey() {
        return KEY_FORMAT.formatted();
    }

    public Long read() {
        String result = redisTemplate.opsForValue().get(generateKey());
        return result == null ? 0L : Long.valueOf(result);
    }

    public Long increase() {
        return redisTemplate.opsForValue().increment(generateKey());
    }
}
