package mg.tpws.restapi.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RegisterDTO {
    private String email;
    private String name;
    private String password;
}
