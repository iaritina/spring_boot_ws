package mg.tpws.restapi.dto.ticketAssignment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.tpws.restapi.model.TicketPriority;
import mg.tpws.restapi.model.TicketStatus;

import java.util.List;

@Schema(
        name = "TicketAssignmentDetailResponseDTO",
        description = "Details des assignations d'un ticket avec les agents associes"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketAssignmentDetailResponseDTO {

    @Schema(
            description = "Identifiant du ticket",
            example = "12"
    )
    private Long ticketId;

    @Schema(
            description = "Titre du ticket",
            example = "Impossible de se connecter"
    )
    private String ticketTitle;

    @Schema(
            description = "Description detaillee du ticket",
            example = "L'utilisateur ne parvient pas a se connecter a son compte."
    )
    private String ticketDescription;

    @Schema(
            description = "Statut actuel du ticket",
            example = "OPEN",
            allowableValues = {"OPEN", "IN_PROGRESS", "RESOLVED", "CLOSED"}
    )
    private TicketStatus ticketStatus;

    @Schema(
            description = "Niveau de priorite du ticket",
            example = "HIGH",
            allowableValues = {"LOW", "MEDIUM", "HIGH"}
    )
    private TicketPriority ticketPriority;

    @Schema(
            description = "Liste des agents assignes a ce ticket"
    )
    private List<AgentDTO> agents;
}