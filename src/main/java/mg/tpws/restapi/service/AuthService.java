package mg.tpws.restapi.service;

import mg.tpws.restapi.dto.LoginDTO;
import mg.tpws.restapi.dto.user.RegisterDTO;
import mg.tpws.restapi.model.User;
import mg.tpws.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private UserRepository userRepository;

    private JwtService jwtService;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository,
                       JwtService jwtService, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String login(LoginDTO dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password");
        }


        User user = userRepository.findUserByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtService.generateToken(user);
    }

    public Map register(RegisterDTO dto) {
        String hashedPwd = passwordEncoder.encode(dto.getPassword());

        User user;

        if (dto.getRole() != null) {
            user = userRepository.save(
                    new User(dto.getEmail(), dto.getName(), hashedPwd, dto.getRole())
            );
        } else {
            user = userRepository.save(
                    new User(dto.getEmail(), dto.getName(), hashedPwd)
            );
        }

        return Map.of("ID", user.getId(), "Email", user.getEmail());
    }


}
