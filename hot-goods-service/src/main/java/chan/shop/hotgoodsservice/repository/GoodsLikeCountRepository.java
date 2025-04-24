package chan.shop.hotgoodsservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class GoodsLikeCountRepository {
    private final StringRedisTemplate redisTemplate;

    // hot-goods::goods::{goodsId}::like-count
    private static final String KEY_FORMAT = "hot-goods::goods::%s::like-count";

    public void createOrUpdate(Long goodsId, Long likeCount, Duration ttl) {
        redisTemplate.opsForValue().set(generateKey(goodsId), String.valueOf(likeCount), ttl);
    }

    public Long read(Long goodsId) {
        String result = redisTemplate.opsForValue().get(generateKey(goodsId));
        return result == null ? 0L : Long.valueOf(result);
    }

    private String generateKey(Long goodsId) {
        return KEY_FORMAT.formatted(goodsId);
    }
}
