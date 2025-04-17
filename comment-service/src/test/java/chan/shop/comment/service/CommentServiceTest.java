package chan.shop.comment.service;

import chan.shop.commentService.entity.Comment;
import chan.shop.commentService.repository.CommentRepository;
import chan.shop.commentService.service.CommentService;
import chan.shop.commentService.service.CommentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    CommentServiceImpl commentService;
    @Mock
    CommentRepository commentRepository;

    @Test
    @DisplayName("삭제할 댓글이 자식이 있으면, 삭제 표시만 함")
    void deleteShouldMarkDeleteIfHasChildren() {
        //given
        Long goodsId = 1L;
        Long commentId = 2L;
        Comment comment = createComment(goodsId, commentId);
        given(commentRepository.findById(commentId))
                .willReturn(Optional.of(comment));
        given(commentRepository.countBy(goodsId, commentId, 2L)).willReturn(2L);

        //when
        commentService.delete(commentId);

        //then
        verify(comment).delete();
    }

    @Test
    @DisplayName("하위 댓글이 삭제될 때, 삭제되지 않은 부모가 존재하면, 하위 댓글만 삭제함")
    void deleteShouldDeleteChildOnlyIfNotDeletedParent() {
        //given
        Long goodsId = 1L;
        Long commentId = 2L;
        Long parentCommentId = 1L;

        Comment comment = createComment(goodsId, commentId, parentCommentId);
        given(comment.isRoot()).willReturn(false); // Root 아님

        Comment parentComment = mock(Comment.class);
        given(parentComment.getDeleted()).willReturn(false); // 삭제되지 않은 부모

        given(commentRepository.findById(commentId))
                .willReturn(Optional.of(comment));

        given(commentRepository.countBy(goodsId, commentId, 2L)).willReturn(1L); // 자식 없음

        given(commentRepository.findById(parentCommentId))
                .willReturn(Optional.of(parentComment)); //삭제한 댓글이 Root 가 아니면, 상위 댓글 검사

        //when
        commentService.delete(commentId);

        //then
        verify(commentRepository).delete(comment); // 하위 댓글 삭제
        verify(commentRepository, never()).delete(parentComment); // 삭제되지 않은 부모는 삭제되지 않음
    }

    @Test
    @DisplayName("하위 댓글이 삭제될 때, 삭제된 부모가 존재하면, 재귀적으로 모두 삭제")
    void deleteShouldDeleteAllRecursivelyIfDeletedParent() {
        //given
        Long goodsId = 1L;
        Long commentId = 2L;
        Long parentCommentId = 1L;

        Comment comment = createComment(goodsId, commentId, parentCommentId);
        given(comment.isRoot()).willReturn(false);

        Comment parentComment = createComment(goodsId, parentCommentId);
        given(parentComment.isRoot()).willReturn(true);
        given(parentComment.getDeleted()).willReturn(true);

        given(commentRepository.findById(commentId))
                .willReturn(Optional.of(comment));
        given(commentRepository.countBy(goodsId, commentId, 2L)).willReturn(1L);

        given(commentRepository.findById(parentCommentId))
                .willReturn(Optional.of(parentComment));
        given(commentRepository.countBy(goodsId, parentCommentId, 2L)).willReturn(1L);

        //when
        commentService.delete(commentId);

        //then
        verify(commentRepository).delete(comment);
        verify(commentRepository).delete(parentComment);
    }

    private Comment createComment(Long goodsId, Long commentId) {
        Comment comment = mock(Comment.class);
        given(comment.getGoodsId()).willReturn(goodsId);
        given(comment.getCommentId()).willReturn(commentId);
        return comment;
    }

    private Comment createComment(Long goodsId, Long commentId, Long parentCommentId) {
        Comment comment = createComment(goodsId, commentId);
        given(comment.getParentCommentId()).willReturn(parentCommentId);
        return comment;
    }
}
