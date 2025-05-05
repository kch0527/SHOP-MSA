package chan.shop.goodsread.repository;

import chan.shop.event.payload.*;
import chan.shop.goodsread.client.GoodsClient;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GoodsQueryModel {
    private Long goodsId;
    private String goodsTitle;
    private String goodsContent;
    private Long price;
    private Long qty;
    private Long brandId;
    private Long regId;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    private Long goodsCommentCount;
    private Long goodsLikeCount;

    public static GoodsQueryModel create(GoodsCreatedEventPayload payload) {
        GoodsQueryModel goodsQueryModel = new GoodsQueryModel();
        goodsQueryModel.goodsId = payload.getGoodsId();
        goodsQueryModel.goodsTitle = payload.getGoodsTitle();
        goodsQueryModel.goodsContent = payload.getGoodsContent();
        goodsQueryModel.price = payload.getPrice();
        goodsQueryModel.qty = payload.getQty();
        goodsQueryModel.brandId = payload.getBrandId();
        goodsQueryModel.regId = payload.getRegId();
        goodsQueryModel.createAt = payload.getCreateAt();
        goodsQueryModel.modifiedAt = payload.getModifiedAt();
        goodsQueryModel.goodsCommentCount = 0L;
        goodsQueryModel.goodsLikeCount = 0L;
        return goodsQueryModel;
    }

    public static GoodsQueryModel create(GoodsClient.GoodsResponse goods, Long commentCount, Long likeCount) {
        GoodsQueryModel goodsQueryModel = new GoodsQueryModel();
        goodsQueryModel.goodsId = goods.getGoodsId();
        goodsQueryModel.goodsTitle = goods.getGoodsTitle();
        goodsQueryModel.goodsContent = goods.getGoodsContent();
        goodsQueryModel.price = goods.getPrice();
        goodsQueryModel.qty = goods.getQty();
        goodsQueryModel.brandId = goods.getBrandId();
        goodsQueryModel.regId = goods.getRegId();
        goodsQueryModel.createAt = goods.getCreateAt();
        goodsQueryModel.modifiedAt = goods.getModifiedAt();
        goodsQueryModel.goodsCommentCount = commentCount;
        goodsQueryModel.goodsLikeCount = likeCount;
        return  goodsQueryModel;
    }

    public void update(GoodsUpdatedEventPayload payload) {
        this.goodsTitle = payload.getGoodsTitle();
        this.goodsContent = payload.getGoodsContent();
        this.price = payload.getPrice();
        this.qty = payload.getQty();
        this.brandId = payload.getBrandId();
        this.regId = payload.getRegId();
        this.createAt = payload.getCreateAt();
        this.modifiedAt = payload.getModifiedAt();
    }

    public void update(CommentCreatedEventPayload payload) {
        this.goodsCommentCount = payload.getGoodsCommentCount();
    }

    public void update(CommentDeletedEventPayload payload) {
        this.goodsCommentCount = payload.getGoodsCommentCount();
    }

    public void update(GoodsLikeEventPayload payload) {
        this.goodsLikeCount = payload.getGoodsLikeCount();
    }

    public void update(GoodsUnlikeEventPayload payload) {
        this.goodsLikeCount = payload.getGoodsLikeCount();
    }
}
