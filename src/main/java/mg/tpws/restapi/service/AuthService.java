package mg.tpws.restapi.service;

import mg.tpws.restapi.dto.LoginDTO;
import mg.tpws.restapi.dto.RegisterDTO;
import mg.tpws.restapi.model.User;
import mg.tpws.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /*public String login(LoginDTO dto) {
        User user = userRepository
                .findUserByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtService.generateToken(user);
    }*/


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

        User user = userRepository
                .save(new User(dto.getEmail(), hashedPwd));

        return Map.of("ID", user.getId(), "Email", user.getEmail());
    }


}
