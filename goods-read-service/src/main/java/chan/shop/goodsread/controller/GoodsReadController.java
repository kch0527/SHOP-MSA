package chan.shop.goodsread.controller;

import chan.shop.goodsread.response.GoodsReadPageResponse;
import chan.shop.goodsread.response.GoodsReadResponse;
import chan.shop.goodsread.service.GoodsReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoodsReadController {
    private final GoodsReadService goodsReadService;

    @GetMapping("/goods/{goodsId}")
    public GoodsReadResponse read(@PathVariable("goodsId") Long goodsId) {
        return goodsReadService.read(goodsId);
    }

    @GetMapping("/goods")
    public GoodsReadPageResponse readAll(@RequestParam("brandId") Long brandId, @RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        return goodsReadService.readAll(brandId, page, pageSize);
    }

    @GetMapping("goods/infinite-scroll")
    public List<GoodsReadResponse> readAllInfiniteScroll(@RequestParam("brandId") Long brandId,
                                                         @RequestParam(value = "lastGoodsId", required = false) Long lastGoodsId,
                                                         @RequestParam("pageSize") Long pageSize) {
        return goodsReadService.readAllInfiniteScroll(brandId, lastGoodsId, pageSize);
    }
}
