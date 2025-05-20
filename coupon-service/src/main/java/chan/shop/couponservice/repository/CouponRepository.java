package chan.shop.couponservice.repository;

import chan.shop.couponservice.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
