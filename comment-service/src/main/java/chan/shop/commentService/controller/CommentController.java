package chan.shop.commentService.controller;

import chan.shop.commentService.request.CommentCreateRequest;
import chan.shop.commentService.response.CommentPageResponse;
import chan.shop.commentService.response.CommentResponse;
import chan.shop.commentService.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments/{commentId}")
    public CommentResponse read(@PathVariable("commentId") Long commentId) {
        return commentService.read(commentId);
    }

    @PostMapping("/comments")
    public CommentResponse create(@RequestBody CommentCreateRequest request) {
        return commentService.create(request);
    }

    @DeleteMapping("/comments/{commentId}")
    public void delete (@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }

    @GetMapping("/comments")
    public CommentPageResponse readAll(@RequestParam("goodsId") Long goodsId, @RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        return commentService.readAll(goodsId, page, pageSize);
    }

    @GetMapping("/comments/infinite-scroll")
    public List<CommentResponse> readAll(@RequestParam("goodsId") Long goodsId, @RequestParam(value = "lastParentCommentId", required = false) Long lastParentCommentId, @RequestParam(value = "lastCommentId", required = false) Long lastCommentId, @RequestParam("pageSize") Long pageSize) {
        return commentService.readAll(goodsId, lastParentCommentId, lastCommentId, pageSize);
    }

}
