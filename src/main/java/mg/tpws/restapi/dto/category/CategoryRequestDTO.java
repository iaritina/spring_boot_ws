package mg.tpws.restapi.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(
        name = "CategoryRequestDTO",
        description = "Donnees necessaires pour creer ou modifier une categorie"
)
@Setter @Getter
public class CategoryRequestDTO {
    @NotBlank
    @Schema(
            description = "Nom de la categorie",
            example = "Authentification",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Description de la categorie",
            example = "Tickets lies a la connexion, a l'inscription et a la gestion des acces"
    )
    private String description;
}
