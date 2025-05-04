package chan.shop.comment.api;

import chan.shop.commentService.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiV2Test {
    RestClient restClient = RestClient.create("http://localhost:60769");

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

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequestV2 {
        private Long goodsId;
        private String content;
        private String parentPath;
        private Long regId;
    }
}
