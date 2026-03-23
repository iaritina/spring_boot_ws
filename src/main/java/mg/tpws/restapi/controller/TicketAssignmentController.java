package mg.tpws.restapi.controller;

import mg.tpws.restapi.dto.ticketAssignment.AssignedTicketResponseDTO;
import mg.tpws.restapi.dto.ticketAssignment.TicketAssignmentDetailResponseDTO;
import mg.tpws.restapi.dto.ticketAssignment.TicketAssignmentResponseDTO;
import mg.tpws.restapi.model.Ticket;
import mg.tpws.restapi.model.TicketAssignment;
import mg.tpws.restapi.service.TicketAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class TicketAssignmentController {
    private final TicketAssignmentService ticketAssignmentService;

    public TicketAssignmentController(TicketAssignmentService ticketAssignmentService) {
        this.ticketAssignmentService = ticketAssignmentService;
    }

    @PostMapping("/ticket/{ticketId}/agent/{agentId}")
    public ResponseEntity<TicketAssignmentResponseDTO> assignTicketToAgent(
            @PathVariable Long ticketId,
            @PathVariable Long agentId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketAssignmentService.assignTicketToAgent(ticketId, agentId));
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<AssignedTicketResponseDTO>> getTicketsByAgent(@PathVariable Long agentId) {
        return ResponseEntity.ok(ticketAssignmentService.getTicketsByAgent(agentId));
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketAssignmentDetailResponseDTO> getAssignmentsByTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketAssignmentService.getAssignmentsByTicket(ticketId));
    }
}
