package mg.tpws.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mg.tpws.restapi.dto.ticketAssignment.AssignedTicketResponseDTO;
import mg.tpws.restapi.dto.ticketAssignment.TicketAssignmentDetailResponseDTO;
import mg.tpws.restapi.dto.ticketAssignment.TicketAssignmentResponseDTO;
import mg.tpws.restapi.service.TicketAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@Tag(name = "Ticket Assignments", description = "Endpoints d'assignation des tickets aux agents")
public class TicketAssignmentController {
    private final TicketAssignmentService ticketAssignmentService;

    public TicketAssignmentController(TicketAssignmentService ticketAssignmentService) {
        this.ticketAssignmentService = ticketAssignmentService;
    }

    @PostMapping("/ticket/{ticketId}/agent/{agentId}")
    @Operation(
            summary = "Assigner un ticket a un agent",
            description = "Cree une nouvelle assignation entre un ticket et un agent"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Ticket assigne avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketAssignmentResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<TicketAssignmentResponseDTO> assignTicketToAgent(
            @PathVariable Long ticketId,
            @PathVariable Long agentId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketAssignmentService.assignTicketToAgent(ticketId, agentId));
    }

    @GetMapping("/agent/{agentId}")
    @Operation(
            summary = "Lister les tickets assignes a un agent",
            description = "Retourne tous les tickets actuellement assignes a un agent donne"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des tickets assignes retournee avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AssignedTicketResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<List<AssignedTicketResponseDTO>> getTicketsByAgent(@PathVariable Long agentId) {
        return ResponseEntity.ok(ticketAssignmentService.getTicketsByAgent(agentId));
    }

    @GetMapping("/ticket/{ticketId}")
    @Operation(
            summary = "Recuperer les assignations d'un ticket",
            description = "Retourne le detail des assignations associees a un ticket"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Details des assignations retournes avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketAssignmentDetailResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<TicketAssignmentDetailResponseDTO> getAssignmentsByTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketAssignmentService.getAssignmentsByTicket(ticketId));
    }
}
