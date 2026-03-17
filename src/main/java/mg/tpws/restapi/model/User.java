package mg.tpws.restapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "users")
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private RoleName role = RoleName.ROLE_USER;

    public User(String email, String name ,String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User(String email, String name ,String password, RoleName role){
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
