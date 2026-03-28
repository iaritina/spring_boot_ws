package mg.tpws.restapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.tpws.restapi.model.RoleName;

@Schema(
        name = "RegisterDTO",
        description = "Donnees necessaires pour creer un nouveau compte utilisateur"
)
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank
    @Email
    @Schema(
            description = "Adresse e-mail unique de l'utilisateur",
            example = "new.user@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @NotBlank
    @Schema(
            description = "Nom complet de l'utilisateur",
            example = "Jean Dupont",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @NotBlank
    @Schema(
            description = "Mot de passe du compte",
            example = "StrongPassword123",
            requiredMode = Schema.RequiredMode.REQUIRED,
            format = "password"
    )
    private String password;

    @Schema(
            description = "Role attribue au nouvel utilisateur",
            example = "USER",
            allowableValues = {"USER", "ADMIN"}
    )
    private RoleName role;
}
