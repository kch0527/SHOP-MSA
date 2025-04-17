package chan.shop.commentService.response;

import chan.shop.commentService.entity.Comment;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CommentResponse {
    private Long commentId;
    private String content;
    private Long parentCommentId;
    private Long goodsId;
    private Long regId;
    private Boolean deleted;
    private LocalDateTime createAt;

    public static CommentResponse from(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.commentId = comment.getCommentId();
        response.content = comment.getContent();
        response.parentCommentId = comment.getParentCommentId();;
        response.goodsId = comment.getGoodsId();
        response.regId = comment.getRegId();
        response.deleted = comment.getDeleted();
        response.createAt = comment.getCreateAt();

        return response;
    }
}
