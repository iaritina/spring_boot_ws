package mg.tpws.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class JWTUserPrincipal {
    private final String email;
    private final String name;
    private final String role;

    public JWTUserPrincipal(String email, String name, String role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }
}
