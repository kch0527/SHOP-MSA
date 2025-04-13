package chan.shop.goodsService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "goods")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods {
    @Id
    private Long goodsId;
    @Column(nullable = false, length = 100)
    private String goodsTitle;
    @Column(nullable = false, length = 3000)
    private String goodsContent;
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long price;
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long qty;
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long brandId; // shard key
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long regId;
    @Column(nullable = false)
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static Goods create(Long goodsId, String goodsTitle, String goodsContent, Long price, Long qty, Long brandId, Long regId){
        Goods goods = new Goods();
        goods.goodsId = goodsId;
        goods.goodsTitle = goodsTitle;
        goods.goodsContent = goodsContent;
        goods.price = price;
        goods.qty = qty;
        goods.brandId = brandId;
        goods.regId = regId;
        goods.createAt = LocalDateTime.now();
        goods.modifiedAt = goods.createAt;
        return goods;
    }

    public void update(String goodsTitle, String goodsContent, Long price, Long qty){
        this.goodsTitle = goodsTitle;
        this.goodsContent = goodsContent;
        this.price = price;
        this.qty = qty;
        modifiedAt = LocalDateTime.now();
    }
}
