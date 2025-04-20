package chan.shop.viewservice.service;

import chan.shop.viewservice.entity.GoodsViewCount;
import chan.shop.viewservice.repository.GoodsViewCountBackUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GoodsViewCountBackUpProcessor {
    private final GoodsViewCountBackUpRepository goodsViewCountBackUpRepository;

    @Transactional
    public void backUp(Long goodsId, Long viewCount) {
        int result = goodsViewCountBackUpRepository.updateViewCount(goodsId, viewCount);
        if (result == 0) {
            goodsViewCountBackUpRepository.findById(goodsId)
                    .ifPresentOrElse(ignored -> { },
                            () -> goodsViewCountBackUpRepository.save(
                                    GoodsViewCount.init(goodsId, viewCount)
                            ));
        }
    }
}
