package mg.tpws.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mg.tpws.restapi.dto.category.CategoryRequestDTO;
import mg.tpws.restapi.model.Category;
import mg.tpws.restapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Endpoints de gestion des categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(
            summary = "Lister les categories",
            description = "Retourne toutes les categories disponibles"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des categories retournee avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Category.class)
                    )
            )
    })
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Recuperer une categorie par identifiant",
            description = "Retourne le detail d'une categorie a partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categorie trouvee",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Category.class)
                    )
            )
    })
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Creer une categorie",
            description = "Cree une nouvelle categorie a partir des donnees fournies"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Categorie creee avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Category.class)
                    )
            )
    })
    public ResponseEntity<Category> create(
            @Valid @RequestBody CategoryRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Mettre a jour une categorie",
            description = "Modifie une categorie existante a partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categorie mise a jour avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Category.class)
                    )
            )
    })
    public ResponseEntity<Category> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO dto
    ) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer une categorie",
            description = "Supprime une categorie a partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categorie supprimee avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = "{\"message\":\"Category deleted successfully\"}")
                    )
            )
    })
    public ResponseEntity<Map> delete(@PathVariable Long id) {
        categoryService.delete(id);

        return ResponseEntity.ok(Map.of("message",
                "Category deleted successfully"));
    }
}
