package chan.shop.likeservice.repository;

import chan.shop.likeservice.entity.GoodsLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsLikeRepository extends JpaRepository<GoodsLike, Long> {
    Optional<GoodsLike> findByGoodsIdAndUserId(Long goodsId, Long userId);
}
