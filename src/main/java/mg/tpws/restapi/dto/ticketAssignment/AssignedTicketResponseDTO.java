package mg.tpws.restapi.dto.ticketAssignment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.tpws.restapi.model.TicketPriority;
import mg.tpws.restapi.model.TicketStatus;

import java.time.LocalDateTime;

@Schema(
        name = "AssignedTicketResponseDTO",
        description = "Donnees d'un ticket assigne a un agent"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignedTicketResponseDTO {

    @Schema(
            description = "Identifiant de l'assignation",
            example = "1"
    )
    private Long assignmentId;

    @Schema(
            description = "Date et heure d'assignation du ticket",
            example = "2026-03-29T10:15:30"
    )
    private LocalDateTime assignedAt;

    @Schema(
            description = "Identifiant du ticket assigne",
            example = "12"
    )
    private Long ticketId;

    @Schema(
            description = "Titre du ticket",
            example = "Impossible de se connecter"
    )
    private String title;

    @Schema(
            description = "Description detaillee du ticket",
            example = "L'utilisateur ne parvient pas a se connecter a son compte."
    )
    private String description;

    @Schema(
            description = "Statut actuel du ticket",
            example = "OPEN",
            allowableValues = {"OPEN", "IN_PROGRESS", "RESOLVED", "CLOSED"}
    )
    private TicketStatus status;

    @Schema(
            description = "Niveau de priorite du ticket",
            example = "HIGH",
            allowableValues = {"LOW", "MEDIUM", "HIGH"}
    )
    private TicketPriority priority;
}