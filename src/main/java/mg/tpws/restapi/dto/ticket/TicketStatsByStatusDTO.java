package mg.tpws.restapi.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.tpws.restapi.model.TicketStatus;

@Schema(
        name = "TicketStatsByStatusDTO",
        description = "Statistiques des tickets regroupes par statut"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatsByStatusDTO {

    @Schema(
            description = "Statut du ticket",
            example = "OPEN",
            allowableValues = {"OPEN", "CLOSED"}
    )
    private TicketStatus status;

    @Schema(
            description = "Nombre total de tickets pour ce statut",
            example = "5"
    )
    private Long total;
}