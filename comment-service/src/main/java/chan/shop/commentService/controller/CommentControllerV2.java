package chan.shop.commentService.controller;

import chan.shop.commentService.request.CommentCreateRequest;
import chan.shop.commentService.request.CommentCreateRequestV2;
import chan.shop.commentService.response.CommentPageResponse;
import chan.shop.commentService.response.CommentResponse;
import chan.shop.commentService.service.CommentService;
import chan.shop.commentService.service.CommentServiceV2Impl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentControllerV2 {
    private final CommentServiceV2Impl commentService;

    @GetMapping("/v2/comments/{commentId}")
    public CommentResponse read(@PathVariable("commentId") Long commentId) {
        return commentService.read(commentId);
    }

    @PostMapping("/v2/comments")
    public CommentResponse create(@RequestBody CommentCreateRequestV2 request) {
        return commentService.create(request);
    }

    @DeleteMapping("/v2/comments/{commentId}")
    public void delete (@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }


}
