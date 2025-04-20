package chan.shop.viewservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GoodsViewCountRepository {
    private final StringRedisTemplate redisTemplate;

    // view::goods::{goods_id}::view_count
    private static final String KEY_FORMAT = "view::goods::%s::view_count";

    private String generateKey(Long goodsId) {
        return KEY_FORMAT.formatted(goodsId);
    }

    public Long read(Long goodsId) {
        String result = redisTemplate.opsForValue().get(generateKey(goodsId));
        return result == null ? 0L : Long.valueOf(result);
    }

    public Long increase(Long goodsId) {
        return redisTemplate.opsForValue().increment(generateKey(goodsId));
    }
}
