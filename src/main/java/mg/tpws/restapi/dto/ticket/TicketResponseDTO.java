package mg.tpws.restapi.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import mg.tpws.restapi.model.TicketPriority;
import mg.tpws.restapi.model.TicketStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Schema(
        name = "TicketResponseDTO",
        description = "Representation d'un ticket dans les reponses API"
)
@Data
@Builder
public class TicketResponseDTO extends RepresentationModel<TicketResponseDTO> {
    @Schema(description = "Identifiant unique du ticket", example = "12")
    private Long id;

    @Schema(description = "Titre du ticket", example = "Impossible de se connecter")
    private String title;

    @Schema(description = "Description detaillee du ticket", example = "L'utilisateur ne parvient pas a se connecter malgre des identifiants valides.")
    private String description;

    @Schema(
            description = "Statut actuel du ticket",
            example = "OPEN",
            allowableValues = {"OPEN", "IN_PROGRESS", "RESOLVED", "CLOSED"}
    )
    private TicketStatus status;

    @Schema(
            description = "Priorite du ticket",
            example = "HIGH",
            allowableValues = {"LOW", "MEDIUM", "HIGH"}
    )
    private TicketPriority priority;

    @Schema(description = "Date de creation du ticket", example = "2026-03-28T10:15:30")
    private LocalDateTime createdAt;

    @Schema(description = "Date de derniere mise a jour du ticket", example = "2026-03-28T11:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Identifiant du createur du ticket", example = "3")
    private Long creatorId;

    @Schema(description = "Nom du createur du ticket", example = "Jean Dupont")
    private String creatorName;

    @Schema(description = "Adresse e-mail du createur du ticket", example = "jean.dupont@example.com")
    private String creatorEmail;

    @Schema(description = "Identifiant de la categorie du ticket", example = "2")
    private Long categoryId;

    @Schema(description = "Nom de la categorie du ticket", example = "Authentification")
    private String categoryName;
}
