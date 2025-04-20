package chan.shop.view.repository;

import chan.shop.viewservice.ViewServiceApplication;
import chan.shop.viewservice.entity.GoodsViewCount;
import chan.shop.viewservice.repository.GoodsViewCountBackUpRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ViewServiceApplication.class)
public class GoodsViewCountBackUpRepositoryTest {
    @Autowired
    GoodsViewCountBackUpRepository goodsViewCountBackupRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @Transactional
    void updateViewCountTest() {
        //given
        goodsViewCountBackupRepository.save(
                GoodsViewCount.init(1L, 10L)
        );
        entityManager.flush();
        entityManager.clear();

        //when
        int result1 = goodsViewCountBackupRepository.updateViewCount(1L, 100L);
        int result2 = goodsViewCountBackupRepository.updateViewCount(1L, 300L);
        int result3 = goodsViewCountBackupRepository.updateViewCount(1L, 200L);

        //then
        assertThat(result1).isEqualTo(1);
        assertThat(result2).isEqualTo(1);
        assertThat(result3).isEqualTo(0);

        GoodsViewCount goodsViewCount = goodsViewCountBackupRepository.findById(1L).get();
        assertThat(goodsViewCount.getViewCount()).isEqualTo(300L);
    }
}
