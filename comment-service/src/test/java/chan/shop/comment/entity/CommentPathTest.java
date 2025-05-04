package chan.shop.comment.entity;

import chan.shop.commentService.entity.CommentPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class CommentPathTest {
    @Test
    void createChildCommentTest() {
        // 최초 댓글 생성 - "00000"
        createChildCommentTest(CommentPath.create(""), null, "00000");

        // 하위 댓글 최초 생성 "00000" - "00000"
        createChildCommentTest(CommentPath.create("00000"), null, "0000000000");

        // 댓글 생성 "00001"
        createChildCommentTest(CommentPath.create(""),"00000", "00001");

        //0000z
        //     abcdz
        //          zzzzz
        //               zzzzz
        //     abce0 <- 생성
        createChildCommentTest(CommentPath.create("0000z"), "0000zabcdzzzzzzzzzzz", "0000zabce0");
    }

    void createChildCommentTest(CommentPath commentPath, String descendantsTopPath, String expectedChildPath) {
        CommentPath childCommentPath = commentPath.createChildCommentPath(descendantsTopPath);
        assertThat(childCommentPath.getPath()).isEqualTo(expectedChildPath);
    }

    @Test
    void createChildCommentPathMaxDepthTest() {
        assertThatThrownBy(() ->
                CommentPath.create("zzzzz".repeat(5)).createChildCommentPath(null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void createChildCommentPathIfChunkOverflowedTest() {
        //given
        CommentPath commentPath = CommentPath.create("");

        //when, then
        assertThatThrownBy(()-> commentPath.createChildCommentPath("zzzzz"))
                .isInstanceOf(IllegalStateException.class);
    }
}