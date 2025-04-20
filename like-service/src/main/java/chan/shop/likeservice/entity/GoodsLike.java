package chan.shop.likeservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "goods_like")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsLike {

    @Id
    private Long goodsLikeId;
    private Long goodsId; //shard key
    private Long userId;
    private LocalDateTime createAt;

    public static GoodsLike create(Long goodsLikeId, Long goodsId, Long userId) {
        GoodsLike goodsLike = new GoodsLike();
        goodsLike.goodsLikeId = goodsLikeId;
        goodsLike.goodsId = goodsId;
        goodsLike.userId = userId;
        goodsLike.createAt = LocalDateTime.now();
        return goodsLike;
    }
}
