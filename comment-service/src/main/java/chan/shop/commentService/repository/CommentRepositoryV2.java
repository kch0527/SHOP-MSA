package chan.shop.commentService.repository;

import chan.shop.commentService.entity.CommentV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepositoryV2 extends JpaRepository<CommentV2, Long> {

    @Query("select c from CommentV2 c where c.commentPath.path = :path")
    Optional<CommentV2> findByPath(@Param("path") String path);

    @Query(
            value = "select path from comment_v2 " +
                    "where goods_id = :goodsId and path > :pathPrefix and path like :pathPrefix% " +
                    "order by path desc limit 1",
            nativeQuery = true
    )
    Optional<String> findDescendantsTopPath(
            @Param("goodsId") Long goodsId,
            @Param("pathPrefix") String pathPrefix
    );
}
