package chan.shop.event.payload;

import chan.shop.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeletedEventPayload implements EventPayload {
    private Long commentId;
    private String content;
    private String path;
    private Long goodsId;
    private Long regId;
    private Boolean deleted;
    private LocalDateTime createAt;
    private Long goodsCommentCount;
}
