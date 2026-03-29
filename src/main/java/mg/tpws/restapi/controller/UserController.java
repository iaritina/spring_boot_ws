package mg.tpws.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mg.tpws.restapi.dto.user.UpdateUserDTO;
import mg.tpws.restapi.dto.user.UserResponseDTO;
import mg.tpws.restapi.model.JWTUserPrincipal;
import mg.tpws.restapi.service.JwtService;
import mg.tpws.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints de gestion des utilisateurs")
public class UserController {

    @Autowired
    private JwtService jwtService;

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping
    @Operation(
            summary = "Lister les utilisateurs",
            description = "Retourne la liste de tous les utilisateurs"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des utilisateurs retournee avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Recuperer un utilisateur par identifiant",
            description = "Retourne les informations d'un utilisateur a partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Utilisateur trouve",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Mettre a jour un utilisateur",
            description = "Modifie les informations d'un utilisateur existant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Utilisateur mis a jour avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateUserDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer un utilisateur",
            description = "Supprime un utilisateur a partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Utilisateur supprime avec succes",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            examples = @ExampleObject(value = "User deleted successfully")
                    )
            )
    })
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }


    @GetMapping("/me")
    @Operation(
            summary = "Recuperer le profil connecte",
            description = "Retourne les informations de l'utilisateur actuellement authentifie"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Profil utilisateur retourne avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JWTUserPrincipal.class)
                    )
            )
    })
    public ResponseEntity<JWTUserPrincipal> getProfile() {

        return ResponseEntity.ok(jwtService.getLoggedInUser());
    }
}
