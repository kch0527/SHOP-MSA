package chan.shop.viewservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class GoodsViewDistributedLockRepository {
    private final StringRedisTemplate redisTemplate;

    // view::goods::{goods_id}::user::{userId}::lock
    private static final String KEY_FORMAT = "view::goods::%s::user::%s::lock";

    public boolean lock(Long goodsId, Long userId, Duration ttl) {
        String key = generateKey(goodsId, userId);
        return redisTemplate.opsForValue().setIfAbsent(key, "", ttl);
    }

    private String generateKey(Long goodsId, Long userId) {
        return KEY_FORMAT.formatted(goodsId, userId);
    }
}
