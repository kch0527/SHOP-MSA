package chan.shop.commentService.repository;

import chan.shop.commentService.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(
            value = "select count(*) from (" +
            "   select comment_id from comment " +
            "   where goods_id = :goodsId and parent_comment_id = :parentCommentId " +
            "   limit :limit" +
            ") c",
            nativeQuery = true
    )
    Long countBy(@Param("goodsId") Long goodsId, @Param("parentCommentId") Long parentCommentId, @Param("limit") Long limit);
}
