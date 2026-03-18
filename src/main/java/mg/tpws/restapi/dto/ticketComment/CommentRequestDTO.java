package mg.tpws.restapi.dto.ticketComment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequestDTO {
    @NotBlank
    private String content;

    @NotNull
    private Long ticketId;

}
