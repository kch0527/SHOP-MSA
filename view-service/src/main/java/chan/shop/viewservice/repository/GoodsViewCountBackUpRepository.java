package chan.shop.viewservice.repository;

import chan.shop.viewservice.entity.GoodsViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsViewCountBackUpRepository extends JpaRepository<GoodsViewCount, Long> {

    @Query(
            value = "update goods_view_count set view_count = :viewCount " +
                    "where goods_id = :goodsId and view_count < :viewCount",
            nativeQuery = true
    )
    @Modifying
    int updateViewCount(@Param("goodsId") Long goodsId, @Param("viewCount") Long viewCount);
}
