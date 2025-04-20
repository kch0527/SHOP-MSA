package chan.shop.viewservice.service;

import chan.shop.viewservice.repository.GoodsViewCountRepository;
import chan.shop.viewservice.repository.GoodsViewDistributedLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class GoodsViewServiceImpl implements GoodsViewService{
    private final GoodsViewCountRepository goodsViewCountRepository;
    private final GoodsViewCountBackUpProcessor goodsViewCountBackUpProcessor;
    private final GoodsViewDistributedLockRepository goodsViewDistributedLockRepository;
    private static final int BACK_UP_BACH_SIZE = 100;
    private static final Duration TTL = Duration.ofMinutes(10);

    public Long increase(Long goodsId, Long userId) {
        if(!goodsViewDistributedLockRepository.lock(goodsId, userId, TTL)) {
            return goodsViewCountRepository.read(goodsId);
        }

        Long count = goodsViewCountRepository.increase(goodsId);
        if (count % BACK_UP_BACH_SIZE == 0) {
            goodsViewCountBackUpProcessor.backUp(goodsId, count);
        }
        return count;
    }

    public Long count(Long goodsId) {
        return goodsViewCountRepository.read(goodsId);
    }
}
