package mg.tpws.restapi.dto.ticket;

import lombok.Builder;
import lombok.Data;
import mg.tpws.restapi.model.TicketPriority;
import mg.tpws.restapi.model.TicketStatus;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponseDTO extends RepresentationModel<TicketResponseDTO> {
    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long creatorId;
    private String creatorName;
    private String creatorEmail;

    private Long categoryId;
    private String categoryName;
}
