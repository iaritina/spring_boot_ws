package mg.tpws.restapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.tpws.restapi.model.RoleName;

@Schema(
        name = "UpdateUserDTO",
        description = "Donnees necessaires pour modifier les informations d'un utilisateur"
)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    @NotBlank
    @Schema(
            description = "Nom complet de l'utilisateur",
            example = "Jean Dupont",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Email
    @NotBlank
    @Schema(
            description = "Adresse e-mail de l'utilisateur",
            example = "jean.dupont@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @NotNull
    @Schema(
            description = "Role attribue a l'utilisateur",
            example = "USER",
            allowableValues = {"USER", "ADMIN"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private RoleName role;

}
