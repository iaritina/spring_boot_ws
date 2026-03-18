package mg.tpws.restapi.controller;

import jakarta.validation.Valid;
import mg.tpws.restapi.dto.ticketComment.CommentRequestDTO;
import mg.tpws.restapi.dto.ticketComment.CommentResponseDTO;
import mg.tpws.restapi.service.JwtService;
import mg.tpws.restapi.service.TicketCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class TicketCommentController {

    private final TicketCommentService ticketCommentService;
    private final JwtService jwtService;

    public TicketCommentController(TicketCommentService ticketCommentService, JwtService jwtService) {
        this.ticketCommentService = ticketCommentService;
        this.jwtService = jwtService;
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<CommentResponseDTO>> findByTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketCommentService.findByTicket(ticketId));
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> create(@Valid @RequestBody CommentRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketCommentService.create(dto, jwtService.getLoggedInUser().getEmail()));
    }
}