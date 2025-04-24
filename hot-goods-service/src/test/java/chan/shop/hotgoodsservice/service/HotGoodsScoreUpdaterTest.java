package chan.shop.hotgoodsservice.service;

import chan.shop.event.Event;
import chan.shop.hotgoodsservice.repository.GoodsCreatedTimeRepository;
import chan.shop.hotgoodsservice.repository.HotGoodsListRepository;
import chan.shop.hotgoodsservice.service.eventhandler.EventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HotGoodsScoreUpdaterTest {

    @InjectMocks
    HotGoodsScoreUpdater hotGoodsScoreUpdater;
    @Mock
    HotGoodsListRepository hotGoodsListRepository;
    @Mock
    HotGoodsScoreCalculator hotGoodsScoreCalculator;
    @Mock
    GoodsCreatedTimeRepository goodsCreatedTimeRepository;

    @Test
    void updateTest() {
        // given
        Long goodsId = 1L;
        Event event = mock(Event.class);
        EventHandler eventHandler = mock(EventHandler.class);

        given(eventHandler.findGoodsId(event)).willReturn(goodsId);
        LocalDateTime time = LocalDateTime.now();
        given(goodsCreatedTimeRepository.read(goodsId)).willReturn(time);

        // when
        hotGoodsScoreUpdater.update(event, eventHandler);

        // then
        verify(eventHandler).handle(event);
        verify(hotGoodsListRepository)
                .add(anyLong(), any(LocalDateTime.class), anyLong(), anyLong(), any(Duration.class));
    }
}