package chan.shop.couponservice.service;

import chan.shop.commonservice.snowflake.Snowflake;
import chan.shop.couponservice.entity.Coupon;
import chan.shop.couponservice.repository.CouponCountRepository;
import chan.shop.couponservice.repository.CouponRepository;
import chan.shop.couponservice.request.CouponCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{

    private final Snowflake snowflake = new Snowflake();
    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;

    private static final String PREFIX = "EVENT2025";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public void create(CouponCreateRequest request) {

        boolean tryCreateCoupon = couponCountRepository.createCoupon(request.getUserId());

        if(!tryCreateCoupon) {
            throw new IllegalStateException("Coupon issuance failed.");
        }

        couponRepository.save(
                Coupon.create(snowflake.nextId(), generateCouponCode(), request.getUserId(), request.getDiscountAmount())
        );
    }

    public Long count() {
        return couponCountRepository.read();
    }

    private String generateCouponCode() {
        StringBuilder sb = new StringBuilder(PREFIX).append("-");
        for (int i = 0; i < CODE_LENGTH; i++) {
            int idx = RANDOM.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(idx));
        }
        return sb.toString();
    }
}
