package mg.tpws.restapi.dto.ticketComment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(
        name = "CommentRequestDTO",
        description = "Donnees necessaires pour creer un commentaire sur un ticket"
)
@Data
public class CommentRequestDTO {
    @NotBlank
    @Schema(
            description = "Contenu du commentaire",
            example = "Le probleme a ete reproduit et transmis a l'equipe technique.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String content;
}
