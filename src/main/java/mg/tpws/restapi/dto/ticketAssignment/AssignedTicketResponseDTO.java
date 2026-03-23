package mg.tpws.restapi.dto.ticketAssignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.tpws.restapi.model.TicketPriority;
import mg.tpws.restapi.model.TicketStatus;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignedTicketResponseDTO {

    private Long assignmentId;
    private LocalDateTime assignedAt;

    private Long ticketId;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
}
