package chan.shop.viewservice.controller;

import chan.shop.viewservice.service.GoodsViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GoodsViewController {
    private final GoodsViewService goodsViewService;

    @PostMapping("/goods-view/goods/{goodsId}/user/{userId}")
    public Long increase (@PathVariable("goodsId") Long goodsId, @PathVariable("userId") Long userId) {
        return goodsViewService.increase(goodsId, userId);
    }

    @GetMapping("/goods-view/goods/{goodsId}/count")
    public Long count(@PathVariable("goodsId") Long goodsId) {
        return goodsViewService.count(goodsId);
    }
}
