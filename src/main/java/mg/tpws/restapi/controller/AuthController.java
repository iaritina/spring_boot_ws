package mg.tpws.restapi.controller;

import mg.tpws.restapi.dto.LoginDTO;
import mg.tpws.restapi.dto.user.RegisterDTO;
import mg.tpws.restapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public Map login(@RequestBody LoginDTO dto) {
        String token =
                service.login(dto);
        if (token == null) {
            return Map.of("message", "invalid login");
        }
        return Map.of("token", token);

    }

    @PostMapping("/register")
    public Map register(@RequestBody RegisterDTO request) {
        return service.register(request);
    }


}