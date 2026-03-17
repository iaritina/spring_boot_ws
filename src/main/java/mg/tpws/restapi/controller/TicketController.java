package mg.tpws.restapi.controller;

import jakarta.validation.Valid;
import mg.tpws.restapi.dto.ticket.TicketRequestDTO;
import mg.tpws.restapi.dto.ticket.TicketResponseDTO;
import mg.tpws.restapi.dto.ticket.TicketUpdateDTO;
import mg.tpws.restapi.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> findAll() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.findById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<TicketResponseDTO>> findMyTickets(Authentication authentication) {
        return ResponseEntity.ok(ticketService.findMyTickets(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> create(@Valid @RequestBody TicketRequestDTO dto,
                                                    Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketService.create(dto, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody TicketUpdateDTO dto) {
        return ResponseEntity.ok(ticketService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.ok("Ticket deleted successfully");
    }
}