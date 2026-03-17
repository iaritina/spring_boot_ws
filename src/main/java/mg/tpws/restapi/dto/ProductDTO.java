package mg.tpws.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @Positive(message = "Price must be positive")
    private double price;
}
