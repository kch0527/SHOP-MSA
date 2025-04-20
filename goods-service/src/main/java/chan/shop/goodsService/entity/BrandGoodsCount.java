package chan.shop.goodsService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "brand_goods_count")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrandGoodsCount {
    @Id
    private Long brandId;
    private Long goodsCount;

    public static BrandGoodsCount init(Long brandId, Long goodsCount) {
        BrandGoodsCount brandGoodsCount = new BrandGoodsCount();
        brandGoodsCount.brandId = brandId;
        brandGoodsCount.goodsCount = goodsCount;
        return brandGoodsCount;
    }
}
