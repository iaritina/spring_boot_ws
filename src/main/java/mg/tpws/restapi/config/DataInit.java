package mg.tpws.restapi.config;

import lombok.RequiredArgsConstructor;
import mg.tpws.restapi.dto.user.RegisterDTO;
import mg.tpws.restapi.model.RoleName;
import mg.tpws.restapi.repository.UserRepository;
import mg.tpws.restapi.service.AuthService;
import mg.tpws.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInit {
    @Autowired
    private final AuthService service;
    @Autowired
    private final UserRepository userRepository;

    @Bean
    CommandLineRunner initUsers() {
        return args -> {
            if (userRepository.count() == 0) {
                service.register(new RegisterDTO(
                        "admin@example.com",
                        "Admin",
                        "admin123",
                        RoleName.ROLE_ADMIN
                ));

                service.register(new RegisterDTO(
                        "agent@example.com",
                        "Agent",
                        "agent123",
                        RoleName.ROLE_AGENT
                ));

                service.register(new RegisterDTO(
                        "user@example.com",
                        "User",
                        "user123",
                        RoleName.ROLE_USER
                ));
            }
        };
    }

}
