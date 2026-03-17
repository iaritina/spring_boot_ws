package mg.tpws.restapi.controller;

import mg.tpws.restapi.model.User;
import mg.tpws.restapi.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<?> getProfile(@RequestHeader(value = "Authorization", required = false) String header) {
        if (header == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token absent");
        }

        String token = header.replace("Bearer ", "");

        if (!jwtService.isValid(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token invalide");
        }

        String email = jwtService.extractEmail(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("email",email));
    }
}
