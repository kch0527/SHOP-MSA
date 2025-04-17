package chan.shop.commentService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "comment")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    private Long commentId;
    @Column(nullable = false, length = 3000)
    private String content;
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long parentCommentId;
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long goodsId; //shard Key
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long regId;
    @Column(nullable = false)
    private Boolean deleted;
    @Column(nullable = false)
    private LocalDateTime createAt;

    public static Comment create(Long commentId, String content, Long parentCommentId, Long goodsId, Long regId) {
        Comment comment = new Comment();
        comment.commentId = commentId;
        comment.content = content;
        comment.parentCommentId = parentCommentId == null ? commentId : parentCommentId;
        comment.goodsId = goodsId;
        comment.regId = regId;
        comment.deleted = false;
        comment.createAt = LocalDateTime.now();
        return comment;
    }

    public boolean isRoot() {
        return parentCommentId.longValue() == commentId;
    }

    public void delete() {
        deleted = true;
    }
}
