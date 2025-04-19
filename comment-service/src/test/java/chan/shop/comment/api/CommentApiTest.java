package chan.shop.comment.api;

import chan.shop.commentService.response.CommentPageResponse;
import chan.shop.commentService.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentApiTest {
    RestClient restClient = RestClient.create("http://localhost:61489");

    @Test
    void create() {
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "comment2", response1.getCommentId(), 2L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "comment3", response1.getCommentId(), 3L));

        System.out.println("commentId=%s".formatted(response1.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response2.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response3.getCommentId()));
    }
    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("/comments/{commentId}", 171874207263764480L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("response : " + response);
    }

    @Test
    void delete(){
        //commentId=171874207263764480
        //	commentId=171874207880327168
        //	commentId=171874207926464512
        restClient.delete()
                .uri("/comments/{commentId}", 171874207926464512L)
                .retrieve()
                .toBodilessEntity();
    }

    @Test
    void readAll() {
        CommentPageResponse response = restClient.get()
                .uri("/comments?goodsId=1&page=1&pageSize=5")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() : " + response.getCommentCount());

        for (CommentResponse comment : response.getComments()) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() : " + comment.getCommentId());
        }
    }
    
    @Test
    void readAllInfiniteScroll() {
        List<CommentResponse> response1 = restClient.get()
                .uri("/comments/infinite-scroll?goodsId=1&pageSize=10")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("firstPage");
        for (CommentResponse comment : response1) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() : " + comment.getCommentId());
        }

        Long lastParentCommentId = response1.getLast().getParentCommentId();
        Long lastCommentId = response1.getLast().getCommentId();

        List<CommentResponse> response2 = restClient.get()
                .uri("/comments/infinite-scroll?goodsId=1&pageSize=10&lastParentCommentId=%s&lastCommentId=%s"
                        .formatted(lastParentCommentId, lastCommentId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("secondPage");
        for (CommentResponse comment : response2) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() : " + comment.getCommentId());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long goodsId;
        private String content;
        private Long parentCommentId;
        private Long regId;
    }

}
