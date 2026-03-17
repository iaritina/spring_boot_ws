package mg.tpws.restapi.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mg.tpws.restapi.model.TicketPriority;

@Data
public class TicketRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private TicketPriority priority;

    @NotNull
    private Long categoryId;
}
