package chan.shop.commentService.repository;

import chan.shop.commentService.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Query(
            value = "select comment.comment_id, comment.content, comment.parent_comment_id, comment.goods_id, " +
                    "comment.reg_id, comment.deleted, comment.create_at " +
                    "from (" +
                    "   select comment_id from comment where goods_id = :goodsId " +
                    "   order by parent_comment_id asc, comment_id asc " +
                    "   limit :limit offset :offset " +
                    ") c left join comment on c.comment_id = comment.comment_id",
            nativeQuery = true
    )
    List<Comment> findAll (@Param("goodsId") Long goodsId, @Param("offset") Long offset, @Param("limit") Long limit);

    @Query(
            value = "select count(*) from (" +
                    "   select comment_id from comment where goods_id = :goodsId limit :limit" +
                    ") c",
            nativeQuery = true
    )
    Long count(@Param("goodsId") Long goodsId, @Param("limit") Long limit);

    @Query(
            value = "select comment.comment_id, comment.content, comment.parent_comment_id, comment.goods_id, " +
                    "comment.reg_id, comment.deleted, comment.create_at " +
                    "from comment " +
                    "where goods_id = :goodsId " +
                    "order by parent_comment_id asc, comment_id asc " +
                    "limit :limit",
            nativeQuery = true
    )
    List<Comment> findAllInfiniteScroll (@Param("goodsId") Long goodsId, @Param("limit") Long limit);

    @Query(
            value = "select comment.comment_id, comment.content, comment.parent_comment_id, comment.goods_id, " +
                    "comment.reg_id, comment.deleted, comment.create_at " +
                    "from comment " +
                    "where goods_id = :goodsId and (" +
                    "   parent_comment_id > :lastParentCommentId or " +
                    "   (parent_comment_id = :lastParentCommentId and comment_id > :lastCommentId) " +
                    ")" +
                    "order by parent_comment_id asc, comment_id asc " +
                    "limit :limit",
            nativeQuery = true
    )
    List<Comment> findAllInfiniteScroll (@Param("goodsId") Long goodsId,
                                         @Param("lastParentCommentId") Long lastParentCommentId,
                                         @Param("lastCommentId") Long lastCommentId,
                                         @Param("limit") Long limit);

}
