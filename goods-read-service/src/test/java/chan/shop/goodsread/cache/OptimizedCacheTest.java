package chan.shop.goodsread.cache;

import lombok.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OptimizedCacheTest {

    @Test
    void parseDataTest() {
        parseDataTest("data", 10);
        parseDataTest(5L, 10);
        parseDataTest(5, 10);
        parseDataTest(new TestClass("test"), 10);
    }

    void parseDataTest(Object data, long ttlSeconds) {
        // given
        OptimizedCache optimizedCache = OptimizedCache.of(data, Duration.ofSeconds(ttlSeconds));
        System.out.println("optimizedCache = " + optimizedCache);

        // when
        Object resolvedData = optimizedCache.parseData(data.getClass());

        // then
        System.out.println("resolvedData = " + resolvedData);
        assertThat(resolvedData).isEqualTo(data);
    }

    @Test
    void isExpiredTest() {
        assertThat(OptimizedCache.of("data", Duration.ofDays(-30)).isExpired()).isTrue();
        assertThat(OptimizedCache.of("data", Duration.ofDays(30)).isExpired()).isFalse();
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestClass {
        String testData;
    }
}