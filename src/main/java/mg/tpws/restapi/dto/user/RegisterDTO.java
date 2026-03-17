package mg.tpws.restapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.tpws.restapi.model.RoleName;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private RoleName role;
}
