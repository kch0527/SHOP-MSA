package chan.shop.goodsread.repository;

import chan.shop.dataserializer.DataSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;

@Repository
@RequiredArgsConstructor
public class GoodsQueryModelRepository {
    private final StringRedisTemplate redisTemplate;

    // goods-read::goods::{goodsId}
    private static final String KEY_FORMAT = "goods-read::goods::%s";

    public void create(GoodsQueryModel goodsQueryModel, Duration ttl) {
        redisTemplate.opsForValue()
                .set(generateKey(goodsQueryModel), DataSerializer.serialize(goodsQueryModel), ttl);
    }

    public void update(GoodsQueryModel goodsQueryModel) {
        redisTemplate.opsForValue().setIfPresent(generateKey(goodsQueryModel), DataSerializer.serialize(goodsQueryModel));
    }

    public void delete(Long goodsId) {
        redisTemplate.delete(generateKey(goodsId));
    }

    public Optional<GoodsQueryModel> read(Long goodsId) {
        return Optional.ofNullable(
                redisTemplate.opsForValue().get(generateKey(goodsId))
        ).map(json -> DataSerializer.deserialize(json, GoodsQueryModel.class));
    }

    private String generateKey(GoodsQueryModel goodsQueryModel) {
        return generateKey(goodsQueryModel.getGoodsId());
    }

    private String generateKey(Long goodsId) {
        return KEY_FORMAT.formatted(goodsId);
    }

    public Map<Long, GoodsQueryModel> readAll(List<Long> goodsIds) {
        List<String> keyList = goodsIds.stream().map(this::generateKey).toList();
        return redisTemplate.opsForValue().multiGet(keyList).stream()
                .filter(Objects::nonNull)
                .map(json -> DataSerializer.deserialize(json, GoodsQueryModel.class))
                .collect(toMap(GoodsQueryModel::getGoodsId, identity()));
    }
}
