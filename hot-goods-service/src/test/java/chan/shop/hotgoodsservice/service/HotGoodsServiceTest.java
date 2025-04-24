package chan.shop.hotgoodsservice.service;

import chan.shop.event.Event;
import chan.shop.event.EventType;
import chan.shop.hotgoodsservice.service.eventhandler.EventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotGoodsServiceTest {

    @InjectMocks
    HotGoodsService hotGoodsService;
    @Mock
    List<EventHandler> eventHandlers;
    @Mock
    HotGoodsScoreUpdater hotGoodsScoreUpdater;

    @Test
    void handleEventIfEventHandlerNotFoundTest() {
        // given
        Event event = mock(Event.class);
        EventHandler eventHandler = mock(EventHandler.class);
        given(eventHandler.supports(event)).willReturn(false);
        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));

        // when
        hotGoodsService.handleEvent(event);

        // then
        verify(eventHandler, never()).handle(event);
        verify(hotGoodsScoreUpdater, never()).update(event, eventHandler);
    }

    @Test
    void handleEventIfGoodsCreatedTest() {
        // given
        Event event = mock(Event.class);
        given(event.getType()).willReturn(EventType.GOODS_CREATED);

        EventHandler eventHandler = mock(EventHandler.class);
        given(eventHandler.supports(event)).willReturn(true);
        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));

        // when
        hotGoodsService.handleEvent(event);

        // then
        verify(eventHandler).handle(event);
        verify(hotGoodsScoreUpdater, never()).update(event, eventHandler);
    }

    @Test
    void handleEventIfGoodsDeletedTest() {
        // given
        Event event = mock(Event.class);
        given(event.getType()).willReturn(EventType.GOODS_DELETED);

        EventHandler eventHandler = mock(EventHandler.class);
        given(eventHandler.supports(event)).willReturn(true);
        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));

        // when
        hotGoodsService.handleEvent(event);

        // then
        verify(eventHandler).handle(event);
        verify(hotGoodsScoreUpdater, never()).update(event, eventHandler);
    }

    @Test
    void handleEventIfScoreUpdatableEventTest() {
        // given
        Event event = mock(Event.class);
        given(event.getType()).willReturn(mock(EventType.class));

        EventHandler eventHandler = mock(EventHandler.class);
        given(eventHandler.supports(event)).willReturn(true);
        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));

        // when
        hotGoodsService.handleEvent(event);

        // then
        verify(eventHandler, never()).handle(event);
        verify(hotGoodsScoreUpdater).update(event, eventHandler);
    }
}