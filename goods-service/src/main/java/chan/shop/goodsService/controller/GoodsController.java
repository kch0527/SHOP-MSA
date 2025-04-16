package chan.shop.goodsService.controller;

import chan.shop.goodsService.request.GoodsCreateRequest;
import chan.shop.goodsService.request.GoodsUpdateRequest;
import chan.shop.goodsService.response.GoodsPageResponse;
import chan.shop.goodsService.response.GoodsResponse;
import chan.shop.goodsService.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @GetMapping("/goods/{goodsId}")
    public GoodsResponse read(@PathVariable Long goodsId) {
        return goodsService.read(goodsId);
    }

    @GetMapping("/goods")
    public GoodsPageResponse readAll(@RequestParam("brandId") Long brandId, @RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        return goodsService.readAll(brandId, page, pageSize);
    }

    @GetMapping("/goods/infinite-scroll")
    public List<GoodsResponse> readAllInfiniteScroll(@RequestParam("brandId") Long brandId,
                                                     @RequestParam("pageSize") Long pageSize,
                                                     @RequestParam(value = "lastGoodsId", required = false) Long lastGoodsId) {
        return goodsService.readAllInfiniteScroll(brandId, pageSize, lastGoodsId);
    }

    @PostMapping("/goods")
    public GoodsResponse create(@RequestBody GoodsCreateRequest request) {
        return goodsService.create(request);
    }

    @PutMapping("/goods/{goodsId}")
    public GoodsResponse update(@PathVariable Long goodsId, @RequestBody GoodsUpdateRequest request) {
        return goodsService.update(goodsId, request);
    }

    @DeleteMapping("/goods/{goodsId}")
    public void delete(@PathVariable Long goodsId) {
        goodsService.delete(goodsId);
    }
}
