package mg.tpws.restapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.tpws.restapi.model.RoleName;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private RoleName role;

}
