package chan.shop.hotgoodsservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Repository
@RequiredArgsConstructor
public class GoodsCreatedTimeRepository {
    private final StringRedisTemplate redisTemplate;

    // hot-goods::goods::{goodsId}::created-time
    private static final String KEY_FORMAT = "hot-goods::goods::%s::created-time";

    public void createOrUpdate(Long goodsId, LocalDateTime createdAt, Duration ttl) {
        redisTemplate.opsForValue().set(
                generateKey(goodsId),
                String.valueOf(createdAt.toInstant(ZoneOffset.UTC).toEpochMilli()),
                ttl
        );
    }

    public void delete(Long goodsId) {
        redisTemplate.delete(generateKey(goodsId));
    }

    public LocalDateTime read(Long goodsId) {
        String result = redisTemplate.opsForValue().get(generateKey(goodsId));
        if (result == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(result)), ZoneOffset.UTC);
    }

    private String generateKey(Long goodsId) {
        return KEY_FORMAT.formatted(goodsId);
    }
}
