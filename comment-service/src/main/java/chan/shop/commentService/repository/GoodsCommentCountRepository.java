package chan.shop.commentService.repository;

import chan.shop.commentService.entity.GoodsCommentCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsCommentCountRepository extends JpaRepository<GoodsCommentCount, Long> {
    @Query(
            value = "update goods_comment_count set comment_count = comment_count + 1 where goods_id = :goodsId",
            nativeQuery = true
    )
    @Modifying
    int increase(@Param("goodsId") Long goodsId);

    @Query(
            value = "update goods_comment_count set comment_count = comment_count - 1 where goods_id = :goodsId",
            nativeQuery = true
    )
    @Modifying
    int decrease(@Param("goodsId") Long goodsId);
}
