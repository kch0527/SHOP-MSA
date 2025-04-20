package chan.shop.likeservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "goods_like_count")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsLikeCount {
    @Id
    private Long goodsId;
    private Long likeCount;
    @Version
    private Long version;

    public static GoodsLikeCount init(Long goodsId, Long likeCount) {
        GoodsLikeCount goodsLikeCount = new GoodsLikeCount();
        goodsLikeCount.goodsId = goodsId;
        goodsLikeCount.likeCount = likeCount;
        return goodsLikeCount;
    }

    public void increase() {
        this.likeCount++;
    }

    public void decrease() {
        this.likeCount--;
    }
}
