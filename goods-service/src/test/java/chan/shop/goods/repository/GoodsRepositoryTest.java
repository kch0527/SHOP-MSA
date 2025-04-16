package chan.shop.goods.repository;

import chan.shop.goodsService.GoodsServiceApplication;
import chan.shop.goodsService.entity.Goods;
import chan.shop.goodsService.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest(classes = GoodsServiceApplication.class)
public class GoodsRepositoryTest {
    @Autowired
    GoodsRepository goodsRepository;

    @Test
    void findAllTest() {
        List<Goods> goodsList = goodsRepository.findAll(1L, 1499970L, 30L);
        log.info("goods.size = {}", goodsList.size());
        for(Goods goods : goodsList) {
            log.info("goods = {}", goods);
        }
    }

    @Test
    void countTest() {
        Long count = goodsRepository.count(1L, 10000L);
        log.info("count = {}", count);
    }

    @Test
    void findInfiniteScrollTest() {
        List<Goods> goodsList = goodsRepository.findAllInfiniteScroll(1L, 30L);
        for (Goods goods : goodsList) {
            log.info("goodsId = {}", goods.getGoodsId());
        }

        Long lastGoodsId = goodsList.getLast().getGoodsId();
        List<Goods> goodsList2 = goodsRepository.findAllInfiniteScroll(1L, 30L, lastGoodsId);
        for (Goods goods : goodsList2) {
            log.info("goodsId = {}", goods.getGoodsId());
        }

    }
}
