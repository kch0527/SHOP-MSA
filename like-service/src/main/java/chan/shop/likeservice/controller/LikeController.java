package chan.shop.likeservice.controller;

import chan.shop.likeservice.response.GoodsLikeResponse;
import chan.shop.likeservice.service.GoodsLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final GoodsLikeService goodsLikeService;

    @GetMapping("/goods-like/goods/{goodsId}/user/{userId}")
    public GoodsLikeResponse read(@PathVariable("goodsId") Long goodsId, @PathVariable("userId") Long userId) {
        return goodsLikeService.read(goodsId, userId);
    }

    @PostMapping("/goods-like/goods/{goodsId}/user/{userId}")
    public void like(@PathVariable("goodsId") Long goodsId, @PathVariable("userId") Long userId) {
        goodsLikeService.like(goodsId, userId);
    }

    @DeleteMapping("/goods-like/goods/{goodsId}/user/{userId}")
    public void unlike(@PathVariable("goodsId") Long goodsId, @PathVariable("userId") Long userId) {
        goodsLikeService.unlike(goodsId, userId);
    }

}
