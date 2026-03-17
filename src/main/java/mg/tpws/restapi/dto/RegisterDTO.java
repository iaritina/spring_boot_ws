package mg.tpws.restapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RegisterDTO {
    private String email;
    private String password;
}
