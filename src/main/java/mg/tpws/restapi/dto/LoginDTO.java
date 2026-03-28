package mg.tpws.restapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "LoginDTO",
        description = "Donnees necessaires pour authentifier un utilisateur"
)
@Getter @Setter
@NoArgsConstructor
public class LoginDTO {
    @Schema(
            description = "Adresse e-mail de l'utilisateur",
            example = "user@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Schema(
            description = "Mot de passe du compte",
            example = "password123",
            requiredMode = Schema.RequiredMode.REQUIRED,
            format = "password"
    )
    private String password;
}
