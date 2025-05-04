package chan.shop.commentService.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "comment_v2")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentV2 {
    @Id
    private Long commentId;
    @Column(nullable = false, length = 3000)
    private String content;
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long goodsId; //shard Key
    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long regId;
    @Embedded
    private CommentPath commentPath;
    @Column(nullable = false)
    private Boolean deleted;
    @Column(nullable = false)
    private LocalDateTime createAt;

    public static CommentV2 create(Long commentId, String content, Long goodsId, Long regId, CommentPath commentPath) {
        CommentV2 commentV2 = new CommentV2();
        commentV2.commentId = commentId;
        commentV2.content = content;
        commentV2.goodsId = goodsId;
        commentV2.regId = regId;
        commentV2.commentPath = commentPath;
        commentV2.deleted = false;
        commentV2.createAt = LocalDateTime.now();
        return commentV2;
    }

    public boolean isRoot() {
        return commentPath.isRoot();
    }

    public void delete() {
        deleted = true;
    }
}
