package chan.shop.viewservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "goods_view_count")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsViewCount {

    @Id
    private Long goodsId;
    private Long viewCount;

    public static GoodsViewCount init(Long goodsId, Long viewCount) {
        GoodsViewCount goodsViewCount = new GoodsViewCount();
        goodsViewCount.goodsId = goodsId;
        goodsViewCount.viewCount = viewCount;
        return goodsViewCount;
    }
}
