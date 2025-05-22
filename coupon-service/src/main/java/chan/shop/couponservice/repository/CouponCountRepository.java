package chan.shop.couponservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponCountRepository {
    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<Long> couponIssueScript;

    // event::coupon_count
    private static final String KEY_FORMAT = "event::coupon_count";
    // event::coupon_count::userId::${userId}
    private static final String USER_KEY_FORMAT = "event::coupon_count::userId::%s";
    private static final long MAX_COUPON_COUNT = 1000;
    private static final long TTL_SECONDS = 3600;

    private String generateKey() {
        return KEY_FORMAT.formatted();
    }

    private String generateUserKey(Long userId) {
        return USER_KEY_FORMAT.formatted(userId);
    }

    public Long read() {
        String result = redisTemplate.opsForValue().get(generateKey());
        return result == null ? 0L : Long.valueOf(result);
    }

    public Long increase() {
        return redisTemplate.opsForValue().increment(generateKey());
    }

    public boolean createCoupon(Long userId) {
        String userKey = generateUserKey(userId);
        List<String> keys = List.of(KEY_FORMAT, userKey);
        List<String> args = List.of(String.valueOf(MAX_COUPON_COUNT), String.valueOf(TTL_SECONDS));

        Long result = redisTemplate.execute(couponIssueScript, keys, args.toArray());

        if(result == null) {
            throw new IllegalStateException("Redis Lua Script Failed.");
        }

        return result == 1;
    }
}
