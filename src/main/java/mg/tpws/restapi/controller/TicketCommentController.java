package mg.tpws.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mg.tpws.restapi.dto.ticketComment.CommentRequestDTO;
import mg.tpws.restapi.dto.ticketComment.CommentResponseDTO;
import mg.tpws.restapi.service.JwtService;
import mg.tpws.restapi.service.TicketCommentService;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets/{ticketId}/comments")
@Tag(name = "Ticket Comments", description = "Endpoints de gestion des commentaires de ticket")
public class TicketCommentController {

    private final TicketCommentService ticketCommentService;
    private final JwtService jwtService;

    public TicketCommentController(TicketCommentService ticketCommentService, JwtService jwtService) {
        this.ticketCommentService = ticketCommentService;
        this.jwtService = jwtService;
    }

    @GetMapping
    @Operation(
            summary = "Lister les commentaires d'un ticket",
            description = "Retourne tous les commentaires associes a un ticket"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des commentaires retournee avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<List<CommentResponseDTO>> findByTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketCommentService.findByTicket(ticketId));
    }

    @PostMapping
    @Operation(
            summary = "Ajouter un commentaire a un ticket",
            description = "Cree un nouveau commentaire sur le ticket cible pour l'utilisateur authentifie"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Commentaire cree avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<CommentResponseDTO> create(@PathVariable Long ticketId,
                                                     @Valid @RequestBody CommentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketCommentService.create(ticketId, dto, jwtService.getLoggedInUser().getEmail()));
    }
}
