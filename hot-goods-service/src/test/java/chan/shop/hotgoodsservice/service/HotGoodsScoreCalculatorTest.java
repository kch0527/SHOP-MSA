package chan.shop.hotgoodsservice.service;

import chan.shop.hotgoodsservice.repository.GoodsCommentCountRepository;
import chan.shop.hotgoodsservice.repository.GoodsLikeCountRepository;
import chan.shop.hotgoodsservice.repository.GoodsViewCountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class HotGoodsScoreCalculatorTest {

    @InjectMocks
    HotGoodsScoreCalculator hotGoodsScoreCalculator;
    @Mock
    GoodsLikeCountRepository goodsLikeCountRepository;
    @Mock
    GoodsViewCountRepository goodsViewCountRepository;
    @Mock
    GoodsCommentCountRepository goodsCommentCountRepository;

    @Test
    void calculateTest() {
        // given
        Long goodsId = 1L;
        long likeCount = RandomGenerator.getDefault().nextLong(100);
        long viewCount = RandomGenerator.getDefault().nextLong(100);
        long commentCount = RandomGenerator.getDefault().nextLong(100);
        given(goodsLikeCountRepository.read(goodsId)).willReturn(likeCount);
        given(goodsViewCountRepository.read(goodsId)).willReturn(viewCount);
        given(goodsCommentCountRepository.read(goodsId)).willReturn(commentCount);

        // when
        long score = hotGoodsScoreCalculator.calculate(goodsId);

        // then
        assertThat(score).isEqualTo(3 * likeCount + 3 * viewCount + 1 * commentCount);

    }
}