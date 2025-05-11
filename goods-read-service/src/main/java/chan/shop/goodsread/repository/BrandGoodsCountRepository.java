package chan.shop.goodsread.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BrandGoodsCountRepository {
    private final StringRedisTemplate redisTemplate;

    private static final String KEY_FORMAT = "goods-read::brand-goods-count::brand::%s";

    public void createOrUpdate(Long brandId, Long goodsCount) {
        redisTemplate.opsForValue().set(generateKey(brandId), String.valueOf(goodsCount));
    }

    public Long read(Long brandId) {
        String result = redisTemplate.opsForValue().get(generateKey(brandId));
        return result == null ? 0L : Long.valueOf(result);
    }

    private String generateKey(Long brandId) {
        return KEY_FORMAT.formatted(brandId);
    }

}
