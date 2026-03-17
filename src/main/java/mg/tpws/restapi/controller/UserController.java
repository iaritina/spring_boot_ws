package mg.tpws.restapi.controller;

import jakarta.validation.Valid;
import mg.tpws.restapi.dto.user.UpdateUserDTO;
import mg.tpws.restapi.dto.user.UserResponseDTO;
import mg.tpws.restapi.model.User;
import mg.tpws.restapi.service.JwtService;
import mg.tpws.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JwtService jwtService;

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    // ADMIN
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateUserDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }


    @GetMapping("/me")
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
