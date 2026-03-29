package mg.tpws.restapi.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mg.tpws.restapi.model.TicketPriority;

@Schema(
        name = "TicketRequestDTO",
        description = "Donnees necessaires pour creer un ticket"
)
@Data
public class TicketRequestDTO {

    @NotBlank
    @Schema(
            description = "Titre court du ticket",
            example = "Impossible de se connecter",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @NotBlank
    @Schema(
            description = "Description detaillee du probleme ou de la demande",
            example = "L'utilisateur ne parvient pas a se connecter malgre des identifiants valides.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;

    @NotNull
    @Schema(
            description = "Niveau de priorite du ticket",
            example = "HIGH",
            allowableValues = {"LOW", "MEDIUM", "HIGH"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TicketPriority priority;

    @NotNull
    @Schema(
            description = "Identifiant de la categorie associee au ticket",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long categoryId;
}
