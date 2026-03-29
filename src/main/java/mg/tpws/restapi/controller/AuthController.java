package mg.tpws.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mg.tpws.restapi.dto.LoginDTO;
import mg.tpws.restapi.dto.user.RegisterDTO;
import mg.tpws.restapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints d'authentification et de creation de compte")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Authentifier un utilisateur",
            description = "Verifie les identifiants d'un utilisateur et retourne un token si l'authentification reussit"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Authentification reussie",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{\"token\":\"eyJhbGciOiJIUzI1NiJ9...\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "200",
                    description = "Identifiants invalides",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{\"message\":\"invalid login\"}")
                    )
            )
    })
    public Map login(@RequestBody LoginDTO dto) {
        String token =
                service.login(dto);
        if (token == null) {
            return Map.of("message", "invalid login");
        }
        return Map.of("token", token);

    }

    @PostMapping("/register")
    @Operation(
            summary = "Creer un compte utilisateur",
            description = "Enregistre un nouvel utilisateur a partir des informations fournies dans le corps de la requete"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Compte cree avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class)
                    )
            )
    })
    public Map register(@RequestBody RegisterDTO request) {
        return service.register(request);
    }


}
