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

    @PostMapping("/goods-like/goods/{goodsId}/user/{userId}/pessimistic-lock-1")
    public void likePessimisticLock1(@PathVariable("goodsId") Long goodsId, @PathVariable("userId") Long userId) {
        goodsLikeService.likePessimisticLock1(goodsId, userId);
    }

    @DeleteMapping("/goods-like/goods/{goodsId}/user/{userId}/pessimistic-lock-1")
    public void unlikePessimisticLock1(@PathVariable("goodsId") Long goodsId, @PathVariable("userId") Long userId) {
        goodsLikeService.unlikePessimisticLock1(goodsId, userId);
    }

    @PostMapping("/goods-like/goods/{goodsId}/user/{userId}/pessimistic-lock-2")
    public void likePessimisticLock2(@PathVariable("goodsId") Long goodsId, @PathVariable("userId") Long userId) {
        goodsLikeService.likePessimisticLock2(goodsId, userId);
    }

    @DeleteMapping("/goods-like/goods/{goodsId}/user/{userId}/pessimistic-lock-2")
    public void unlikePessimisticLock2(@PathVariable("goodsId") Long goodsId, @PathVariable("userId") Long userId) {
        goodsLikeService.unlikePessimisticLock2(goodsId, userId);
    }

    @PostMapping("/goods-like/goods/{goodsId}/user/{userId}/optimistic-lock")
    public void likeOptimisticLock(@PathVariable("goodsId") Long goodsId, @PathVariable("userId") Long userId) {
        goodsLikeService.likeOptimisticLock(goodsId, userId);
    }

    @DeleteMapping("/goods-like/goods/{goodsId}/user/{userId}/optimistic-lock")
    public void unlikeOptimisticLock(@PathVariable("goodsId") Long goodsId, @PathVariable("userId") Long userId) {
        goodsLikeService.unlikeOptimisticLock(goodsId, userId);
    }

    @GetMapping("/goods-like/goods/{goodsId}/count")
    public Long count(@PathVariable("goodsId") Long goodsId) {
        return goodsLikeService.count(goodsId);
    }
}
