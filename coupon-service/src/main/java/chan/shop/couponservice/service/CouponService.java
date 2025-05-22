package chan.shop.couponservice.service;

import chan.shop.couponservice.request.CouponCreateRequest;

public interface CouponService {

    void create(CouponCreateRequest request);
    Long count();
}
