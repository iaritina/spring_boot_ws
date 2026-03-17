package mg.tpws.restapi.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CategoryRequestDTO {
    @NotBlank
    private String name;

    private String description;
}
