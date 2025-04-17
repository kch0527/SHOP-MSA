package chan.shop.commentService.service;

import chan.shop.commentService.entity.Comment;
import chan.shop.commentService.request.CommentCreateRequest;
import chan.shop.commentService.response.CommentResponse;

public interface CommentService {
    CommentResponse create(CommentCreateRequest request);
    CommentResponse read(Long commentId);
    void delete(Long commentId);
}
