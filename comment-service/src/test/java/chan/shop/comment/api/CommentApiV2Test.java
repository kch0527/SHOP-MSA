package chan.shop.comment.api;

import chan.shop.commentService.response.CommentPageResponse;
import chan.shop.commentService.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentApiV2Test {
    RestClient restClient = RestClient.create("http://localhost:55452");

    @Test
    void create() {
        CommentResponse response1 = create(new CommentCreateRequestV2(1L, "test1", null, 1L));
        CommentResponse response2 = create(new CommentCreateRequestV2(1L, "test2", response1.getPath(), 1L));
        CommentResponse response3 = create(new CommentCreateRequestV2(1L, "test3", response2.getPath(), 1L));

        System.out.println("response1.getCommentId() = " + response1.getCommentId());
        System.out.println("response1.getCommentId() = " + response1.getPath());
        System.out.println("\tresponse2.getCommentId() = " + response2.getCommentId());
        System.out.println("\tresponse2.getCommentId() = " + response2.getPath());
        System.out.println("\t\tresponse3.getCommentId() = " + response3.getCommentId());
        System.out.println("\t\tresponse3.getCommentId() = " + response3.getPath());

        /**
         * response1.getCommentId() = 177422614857293824
         * response1.getCommentId() = 00003
         * 	response2.getCommentId() = 177422615197032448
         * 	response2.getCommentId() = 0000300000
         * 		response3.getCommentId() = 177422615247364096
         * 		response3.getCommentId() = 000030000000000
         */
    }

    CommentResponse create(CommentCreateRequestV2 request) {
        return restClient.post()
                .uri("/v2/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("/v2/comments/{commentId}", 177422614857293824L)
                .retrieve()
                .body(CommentResponse.class);
        System.out.println("response = " + response);
    }

    @Test
    void delete() {
        restClient.delete()
                .uri("/v2/comments/{commentId}", 177422614857293824L)
                .retrieve()
                .toBodilessEntity();
    }

    @Test
    void readAll() {
        CommentPageResponse response = restClient.get()
                .uri("/v2/comments?goodsId=1&pageSize=10&page=1")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());
        for (CommentResponse comment : response.getComments()) {
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }
    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponse> response1 = restClient.get()
                .uri("/v2/comments/infinite-scroll?goodsId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("firstPage");
        for (CommentResponse response : response1) {
            System.out.println("response.getCommentId() = " + response.getCommentId());
        }

        String lastPath = response1.getLast().getPath();

        List<CommentResponse> response2 = restClient.get()
                .uri("/v2/comments/infinite-scroll?goodsId=1&pageSize=5&lastPath=%s".formatted(lastPath))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("secondPage");
        for (CommentResponse response : response2) {
            System.out.println("response.getCommentId() = " + response.getCommentId());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequestV2 {
        private Long goodsId;
        private String content;
        private String parentPath;
        private Long regId;
    }
}
