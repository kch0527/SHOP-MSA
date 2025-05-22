package chan.shop.couponservice.controller;

import chan.shop.couponservice.request.CouponCreateRequest;
import chan.shop.couponservice.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/coupon/event-coupon")
    public void increase(@RequestBody CouponCreateRequest request) {
        couponService.create(request);
    }

    @GetMapping("/coupon/event-coupon/count")
    public Long count() {
        return couponService.count();
    }
}
