package chan.shop.couponservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "coupon")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    private Long couponId;
    private String couponCode;
    private Long userId;
    private Long discountAmount;
    private boolean used;
    private LocalDateTime createAt;
    private LocalDateTime useAt;
    private LocalDateTime expireAt;

    public static Coupon create(Long couponId, String couponCode, Long userId, Long discountAmount) {
        Coupon coupon = new Coupon();
        coupon.couponId = couponId;
        coupon.couponCode = couponCode;
        coupon.userId = userId;
        coupon.discountAmount = discountAmount;
        coupon.used = false;
        coupon.createAt = LocalDateTime.now();
        coupon.expireAt = LocalDateTime.now().plusMonths(1);
        return coupon;
    }
}
