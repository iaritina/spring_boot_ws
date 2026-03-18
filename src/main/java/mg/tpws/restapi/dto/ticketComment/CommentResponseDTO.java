package mg.tpws.restapi.dto.ticketComment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    private Long ticketId;

    private Long authorId;
    private String authorName;
    private String authorEmail;
}