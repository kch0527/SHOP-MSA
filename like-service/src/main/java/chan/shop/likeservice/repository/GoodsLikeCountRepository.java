package chan.shop.likeservice.repository;

import chan.shop.likeservice.entity.GoodsLikeCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsLikeCountRepository extends JpaRepository<GoodsLikeCount, Long> {

    //select ... for update
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<GoodsLikeCount> findLockedByGoodsId(Long goodsId);

    @Query(
            value = "update goods_like_count set like_count = like_count + 1 where goods_id = :goodsId",
            nativeQuery = true
    )
    @Modifying
    int increase(@Param("goodsId") Long goodsId);

    @Query(
            value = "update goods_like_count set like_count = like_count - 1 where goods_id = :goodsId",
            nativeQuery = true
    )
    @Modifying
    int decrease(@Param("goodsId") Long goodsId);
}
