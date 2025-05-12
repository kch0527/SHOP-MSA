package chan.shop.goodsread.cache;

import chan.shop.dataserializer.DataSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@ToString
public class OptimizedCache {
    private String data;
    private LocalDateTime expiredAt;

    public static OptimizedCache of(Object data, Duration ttl) {
        OptimizedCache optimizedCache = new OptimizedCache();
        optimizedCache.data = DataSerializer.serialize(data);
        optimizedCache.expiredAt = LocalDateTime.now().plus(ttl);
        return optimizedCache;
    }

    // 논리적으로 완료 확인
    @JsonIgnore
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }

    public <T> T parseData(Class<T> dataType) {
        return DataSerializer.deserialize(data, dataType);
    }
}
