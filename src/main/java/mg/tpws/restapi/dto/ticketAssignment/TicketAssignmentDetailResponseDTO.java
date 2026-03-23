package mg.tpws.restapi.dto.ticketAssignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.tpws.restapi.model.TicketPriority;
import mg.tpws.restapi.model.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketAssignmentDetailResponseDTO {

    private Long ticketId;
    private String ticketTitle;
    private String ticketDescription;
    private TicketStatus ticketStatus;
    private TicketPriority ticketPriority;
    private List<AgentDTO> agents;
}