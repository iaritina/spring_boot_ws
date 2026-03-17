package mg.tpws.restapi.controller;

import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import mg.tpws.restapi.dto.ProductDTO;
import mg.tpws.restapi.model.Product;
import mg.tpws.restapi.service.JwtService;
import mg.tpws.restapi.service.ProductService;
import mg.tpws.restapi.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> create(
            @Valid @RequestBody ProductDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        boolean deleted = service.deleteById(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produit introuvable");
        }

        return ResponseEntity.ok("Produit supprimé avec succès");
    }

}
