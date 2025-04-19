package chan.shop.commentService.service;

import chan.shop.commentService.entity.Comment;
import chan.shop.commentService.request.CommentCreateRequest;
import chan.shop.commentService.response.CommentPageResponse;
import chan.shop.commentService.response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse create(CommentCreateRequest request);
    CommentResponse read(Long commentId);
    void delete(Long commentId);
    CommentPageResponse readAll(Long goodsId, Long page, Long pageSize);
    List<CommentResponse> readAll(Long goodsId, Long lastParentCommentId, Long lastCommentId, Long limit);
}
