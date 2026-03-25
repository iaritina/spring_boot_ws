package mg.tpws.restapi.controller;

import jakarta.validation.Valid;
import mg.tpws.restapi.dto.ticket.*;
import mg.tpws.restapi.service.JwtService;
import mg.tpws.restapi.service.TicketService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final JwtService jwtService;

    public TicketController(TicketService ticketService, JwtService jwtService) {
        this.ticketService = ticketService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<TicketResponseDTO>> findAll() {
        List<TicketResponseDTO> tickets = ticketService.findAll();

        tickets.forEach(ticket -> ticket.add(
                linkTo(methodOn(TicketController.class).findById(ticket.getId())).withSelfRel()
        ));

        CollectionModel<TicketResponseDTO> collectionModel = CollectionModel.of(
                tickets,
                linkTo(methodOn(TicketController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> findById(@PathVariable Long id) {
        TicketResponseDTO dto = ticketService.findById(id);
        dto.add(linkTo(methodOn(TicketController.class).findById(id)).withSelfRel());
        dto.add(linkTo(methodOn(TicketController.class).findAll()).withRel("allTickets"));
        dto.add(linkTo(methodOn(TicketCommentController.class).findByTicket(id)).withRel("comments"));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/me")
    public ResponseEntity<List<TicketResponseDTO>> findMyTickets() {
        return ResponseEntity.ok(ticketService.findMyTickets(jwtService.getLoggedInUser().getEmail()));
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> create(@Valid @RequestBody TicketRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketService.create(dto, jwtService.getLoggedInUser().getEmail()));
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

    @GetMapping("/stats/by-category")
    public ResponseEntity<List<TicketStatsByCategoryDTO>> getStatsByCategory() {
        return ResponseEntity.ok(ticketService.getStatsByCategory());
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<TicketStatsByStatusDTO>> getStatsByStatus() {
        return ResponseEntity.ok(ticketService.getStatsByStatus());
    }
}