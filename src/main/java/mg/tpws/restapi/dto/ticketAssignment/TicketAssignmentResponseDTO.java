package mg.tpws.restapi.dto.ticketAssignment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(
        name = "TicketAssignmentResponseDTO",
        description = "Representation d'une assignation de ticket dans les reponses API"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketAssignmentResponseDTO {
    @Schema(description = "Identifiant unique de l'assignation", example = "7")
    private Long id;

    @Schema(description = "Date et heure d'assignation du ticket", example = "2026-03-28T15:30:00")
    private LocalDateTime assignedAt;

    @Schema(description = "Identifiant du ticket assigne", example = "12")
    private Long ticketId;

    @Schema(description = "Identifiant de l'agent assigne", example = "4")
    private Long agentId;
}
