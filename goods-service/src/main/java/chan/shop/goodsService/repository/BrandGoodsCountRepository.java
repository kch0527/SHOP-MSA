package chan.shop.goodsService.repository;

import chan.shop.goodsService.entity.BrandGoodsCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandGoodsCountRepository extends JpaRepository<BrandGoodsCount, Long> {

    @Query(
            value = "update brand_goods_count set goods_count = goods_count + 1 where brand_id = :brandId",
            nativeQuery = true
    )
    @Modifying
    int increase(@Param("brandId") Long brandId);

    @Query(
            value = "update brand_goods_count set goods_count = goods_count - 1 where brand_id = :brandId",
            nativeQuery = true
    )
    @Modifying
    int decrease(@Param("brandId") Long brandId);
}
