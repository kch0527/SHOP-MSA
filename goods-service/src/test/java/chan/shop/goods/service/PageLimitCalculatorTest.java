package chan.shop.goods.service;

import chan.shop.goodsService.service.PageLimitCalculator;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class PageLimitCalculatorTest {

    @Test
    void calculatePageLimitTest() {
        calculatePageLimitTest(1L, 10L, 10L, 101L);
        calculatePageLimitTest(5L, 10L, 10L, 101L);
        calculatePageLimitTest(11L, 10L, 10L, 201L);
        calculatePageLimitTest(25L, 10L, 10L, 301L);
    }

    void calculatePageLimitTest(Long page, Long pageSize, Long movablePageCount, Long expected) {
        Long result = PageLimitCalculator.calculatorPageLimit(page, pageSize, movablePageCount);
        assertThat(result).isEqualTo(expected);
    }
}
