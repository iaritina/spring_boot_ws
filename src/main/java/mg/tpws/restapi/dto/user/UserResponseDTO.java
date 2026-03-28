package mg.tpws.restapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.tpws.restapi.model.RoleName;

@Schema(
        name = "UserResponseDTO",
        description = "Representation des informations d'un utilisateur dans les reponses API"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    @Schema(
            description = "Identifiant unique de l'utilisateur",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nom complet de l'utilisateur",
            example = "Jean Dupont"
    )
    private String name;

    @Schema(
            description = "Adresse e-mail de l'utilisateur",
            example = "jean.dupont@example.com"
    )
    private String email;

    @Schema(
            description = "Role actuel de l'utilisateur",
            example = "USER",
            allowableValues = {"USER", "ADMIN"}
    )
    private RoleName role;

}
