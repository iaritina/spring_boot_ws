package mg.tpws.restapi.dto.ticketAssignment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "AgentDTO",
        description = "Informations de base sur un agent assigne a un ticket"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDTO {

    @Schema(
            description = "Identifiant de l'agent",
            example = "3"
    )
    private Long id;

    @Schema(
            description = "Nom complet de l'agent",
            example = "John Doe"
    )
    private String name;

    @Schema(
            description = "Adresse email de l'agent",
            example = "john.doe@example.com"
    )
    private String email;
}