package mg.tpws.restapi.dto.ticketComment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(
        name = "CommentResponseDTO",
        description = "Representation d'un commentaire de ticket dans les reponses API"
)
@Data
@Builder
public class CommentResponseDTO {
    @Schema(description = "Identifiant unique du commentaire", example = "5")
    private Long id;

    @Schema(description = "Contenu du commentaire", example = "Le probleme a ete reproduit et est en cours d'analyse.")
    private String content;

    @Schema(description = "Date de creation du commentaire", example = "2026-03-28T14:20:00")
    private LocalDateTime createdAt;

    @Schema(description = "Identifiant du ticket associe", example = "12")
    private Long ticketId;

    @Schema(description = "Identifiant de l'auteur du commentaire", example = "3")
    private Long authorId;

    @Schema(description = "Nom de l'auteur du commentaire", example = "Jean Dupont")
    private String authorName;

    @Schema(description = "Adresse e-mail de l'auteur du commentaire", example = "jean.dupont@example.com")
    private String authorEmail;
}
