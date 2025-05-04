package chan.shop.commentService.request;

import lombok.Getter;

@Getter
public class CommentCreateRequestV2 {
    private Long goodsId;
    private String content;
    private String parentPath;
    private Long regId;
}
