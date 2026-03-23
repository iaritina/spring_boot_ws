package mg.tpws.restapi.dto.ticketAssignment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketAssignmentResponseDTO {
    private Long id;
    private LocalDateTime assignedAt;
    private Long ticketId;
    private Long agentId;
}
