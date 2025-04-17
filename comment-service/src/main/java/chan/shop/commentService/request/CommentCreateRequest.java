package chan.shop.commentService.request;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private Long goodsId;
    private String content;
    private Long parentCommentId;
    private Long regId;
}
