package chan.shop.goodsService.controller;

import chan.shop.goodsService.request.GoodsCreateRequest;
import chan.shop.goodsService.request.GoodsUpdateRequest;
import chan.shop.goodsService.response.GoodsResponse;
import chan.shop.goodsService.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @GetMapping("/goods/{goodsId}")
    public GoodsResponse read(@PathVariable Long goodsId) {
        return goodsService.read(goodsId);
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
