package chan.shop.goodsService.repository;

import chan.shop.goodsService.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query(
            value = "select goods.goods_id, goods.goods_title, goods.goods_content, goods.price, goods.qty, goods.brand_id, goods.reg_id, goods.create_at, goods.modified_at " +
                    "from (" +
                    "   select goods_id from goods " +
                    "   where brand_id = :brandId " +
                    "   order by goods_id desc " +
                    "   limit :limit offset :offset " +
                    ") g left join goods on g.goods_id = goods.goods_id ",
            nativeQuery = true
    )
    List<Goods> findAll(
      @Param("brandId") Long brandId,
      @Param("offset") Long offset,
      @Param("limit") Long limit
    );

    @Query(
            value = "select count(*) from (" +
            "   select goods_id from goods where brand_id = :brandId limit :limit" +
            ") g",
            nativeQuery = true
    )
    Long count(@Param("brandId") Long brandId, @Param("limit") Long limit);

    @Query(
            value = "select goods.goods_id, goods.goods_title, goods.goods_content, goods.price, goods.qty, goods.brand_id, goods.reg_id, goods.create_at, goods.modified_at " +
                    "from goods " +
                    "where brand_id = :brandId " +
                    "order by goods_id desc limit :limit",
            nativeQuery = true
    )
    List<Goods> findAllInfiniteScroll(@Param("brandId") Long brandId, @Param("limit") Long limit);

    @Query(
            value = "select goods.goods_id, goods.goods_title, goods.goods_content, goods.price, goods.qty, goods.brand_id, goods.reg_id, goods.create_at, goods.modified_at " +
                    "from goods " +
                    "where brand_id = :brandId and goods_id < :lastGoodsId " +
                    "order by goods_id desc limit :limit",
            nativeQuery = true
    )
    List<Goods> findAllInfiniteScroll(@Param("brandId") Long brandId, @Param("limit") Long limit, @Param("lastGoodsId") Long lastGoodsId);
}
