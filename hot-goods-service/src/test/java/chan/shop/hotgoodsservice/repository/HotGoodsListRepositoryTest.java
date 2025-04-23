package chan.shop.hotgoodsservice.repository;

import chan.shop.hotgoodsservice.HotGoodsServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HotGoodsServiceApplication.class)
class HotGoodsListRepositoryTest {
    @Autowired
    HotGoodsListRepository hotGoodsListRepository;

    @Test
    void addTest() {
        // given
        LocalDateTime time = LocalDateTime.of(2025, 4, 23,0,0);
        long limit = 3;

        // when
        hotGoodsListRepository.add(1L, time, 2L, limit, Duration.ofSeconds(3));
        hotGoodsListRepository.add(2L, time, 3L, limit, Duration.ofSeconds(3));
        hotGoodsListRepository.add(3L, time, 1L, limit, Duration.ofSeconds(3));
        hotGoodsListRepository.add(4L, time, 5L, limit, Duration.ofSeconds(3));
        hotGoodsListRepository.add(5L, time, 4L, limit, Duration.ofSeconds(3));

        // then
        List<Long> goodsIds = hotGoodsListRepository.readALl("20250423");

        assertThat(goodsIds).hasSize(Long.valueOf(limit).intValue());
        assertThat(goodsIds.get(0)).isEqualTo(4);
        assertThat(goodsIds.get(1)).isEqualTo(5);
        assertThat(goodsIds.get(2)).isEqualTo(2);

        //TimeUnit.SECONDS.sleep(5);
        //assertThat(hotGoodsListRepository.readALl("20250423")).isEmpty();
    }

}