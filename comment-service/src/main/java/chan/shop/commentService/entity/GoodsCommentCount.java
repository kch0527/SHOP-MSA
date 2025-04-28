package chan.shop.commentService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "goods_comment_count")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsCommentCount {

    @Id
    private Long goodsId;
    private Long commentCount;

    public static GoodsCommentCount init(Long goodsId, Long commentCount) {
        GoodsCommentCount goodsCommentCount = new GoodsCommentCount();
        goodsCommentCount.goodsId = goodsId;
        goodsCommentCount.commentCount = commentCount;
        return goodsCommentCount;
    }
}
