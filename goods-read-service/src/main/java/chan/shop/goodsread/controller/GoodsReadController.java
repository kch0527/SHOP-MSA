package chan.shop.goodsread.controller;

import chan.shop.goodsread.response.GoodsReadResponse;
import chan.shop.goodsread.service.GoodsReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GoodsReadController {
    private final GoodsReadService goodsReadService;

    @GetMapping("/goods/{goodsId}")
    public GoodsReadResponse read(@PathVariable("goodsId") Long goodsId) {
        return goodsReadService.read(goodsId);
    }
}
