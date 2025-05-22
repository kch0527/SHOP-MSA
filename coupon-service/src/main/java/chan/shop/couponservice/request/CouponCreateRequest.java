package chan.shop.couponservice.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CouponCreateRequest {
    private Long userId;
    private Long discountAmount;
}
