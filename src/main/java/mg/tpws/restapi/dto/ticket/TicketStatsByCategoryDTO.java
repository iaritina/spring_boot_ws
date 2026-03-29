package mg.tpws.restapi.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(
        name = "TicketStatsByCategoryDTO",
        description = "Statistiques des tickets regroupes par categorie"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatsByCategoryDTO {

    @Schema(
            description = "Nom de la categorie",
            example = "Authentification"
    )
    private String categoryName;

    @Schema(
            description = "Nombre total de tickets dans cette categorie",
            example = "8"
    )
    private Long total;
}