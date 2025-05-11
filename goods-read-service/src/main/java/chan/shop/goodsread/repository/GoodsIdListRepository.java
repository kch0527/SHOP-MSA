package chan.shop.goodsread.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoodsIdListRepository {
    private final StringRedisTemplate redisTemplate;

    private static final String KEY_FORMAT = "goods-read::brand::%s::goods-list";

    public void add(Long brandId, Long goodsId, Long limit) {
        redisTemplate.executePipelined((RedisCallback<?>) action -> {
            StringRedisConnection conn = (StringRedisConnection) action;
            String key = generateKey(brandId);
            conn.zAdd(key, 0, toPaddedString(goodsId));
            conn.zRemRange(key, 0, - limit - 1);
            return null;
        });
    }

    public void delete(Long brandId, Long goodsId) {
        redisTemplate.opsForZSet().remove(generateKey(brandId), toPaddedString(goodsId));
    }

    public List<Long> readAll(Long brandId, Long offset, Long limit) {
        return redisTemplate.opsForZSet()
                .reverseRange(generateKey(brandId), offset, offset + limit - 1)
                .stream().map(Long::valueOf).toList();
    }

    public List<Long> readAllInfiniteScroll(Long brandId, Long lastGoodsId, Long limit) {
        return redisTemplate.opsForZSet().reverseRangeByLex(
                generateKey(brandId),
                lastGoodsId == null ?
                        Range.unbounded() :
                        Range.leftUnbounded(Range.Bound.exclusive(toPaddedString(lastGoodsId))),
                Limit.limit().count(limit.intValue())
        ).stream().map(Long::valueOf).toList();
    }

    // goodsId -> 고정된 자릿 수의 문자열로 변환
    private String toPaddedString(Long goodsId) {
        return "%019d".formatted(goodsId);
    }

    private String generateKey(Long brandId) {
        return KEY_FORMAT.formatted(brandId);
    }
}
