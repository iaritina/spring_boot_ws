package mg.tpws.restapi.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mg.tpws.restapi.model.TicketPriority;
import mg.tpws.restapi.model.TicketStatus;
@Data
public class TicketUpdateDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private TicketPriority priority;

    @NotNull
    private TicketStatus status;

    @NotNull
    private Long categoryId;
}
