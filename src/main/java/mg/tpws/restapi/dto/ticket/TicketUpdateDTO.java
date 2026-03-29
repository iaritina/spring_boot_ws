package mg.tpws.restapi.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mg.tpws.restapi.model.TicketPriority;
import mg.tpws.restapi.model.TicketStatus;

@Schema(
        name = "TicketUpdateDTO",
        description = "Donnees necessaires pour modifier un ticket existant"
)
@Data
public class TicketUpdateDTO {
    @NotBlank
    @Schema(
            description = "Titre court du ticket",
            example = "Impossible de se connecter au portail",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @NotBlank
    @Schema(
            description = "Description detaillee du ticket",
            example = "Le probleme persiste apres reinitialisation du mot de passe.",
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
            description = "Statut actuel du ticket",
            example = "IN_PROGRESS",
            allowableValues = {"OPEN", "IN_PROGRESS", "RESOLVED", "CLOSED"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TicketStatus status;

    @NotNull
    @Schema(
            description = "Identifiant de la categorie associee au ticket",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long categoryId;
}
