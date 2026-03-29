package mg.tpws.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mg.tpws.restapi.dto.ticket.*;
import mg.tpws.restapi.dto.ticketAssignment.AssignedTicketResponseDTO;
import mg.tpws.restapi.service.JwtService;
import mg.tpws.restapi.service.TicketAssignmentService;
import mg.tpws.restapi.service.TicketService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets", description = "Endpoints de gestion des tickets")
public class TicketController {

    private final TicketService ticketService;
    private final JwtService jwtService;

    private final TicketAssignmentService ticketAssignmentService;

    public TicketController(TicketService ticketService, JwtService jwtService, TicketAssignmentService ticketAssignmentService) {
        this.ticketService = ticketService;
        this.jwtService = jwtService;
        this.ticketAssignmentService = ticketAssignmentService;
    }

    @GetMapping
    @Operation(
            summary = "Lister tous les tickets",
            description = "Retourne tous les tickets avec leurs liens HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des tickets retournee avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CollectionModel.class)
                    )
            )
    })
    public ResponseEntity<CollectionModel<TicketResponseDTO>> findAll() {
        List<TicketResponseDTO> tickets = ticketService.findAll();

        tickets.forEach(this::addTicketLinks);

        CollectionModel<TicketResponseDTO> collectionModel = CollectionModel.of(
                tickets,
                linkTo(methodOn(TicketController.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Recuperer un ticket par identifiant",
            description = "Retourne le detail d'un ticket avec ses liens HATEOAS"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket trouve",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<TicketResponseDTO> findById(@PathVariable Long id) {
        TicketResponseDTO dto = ticketService.findById(id);
        addTicketLinks(dto);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Lister mes tickets",
            description = "Retourne les tickets crees par l'utilisateur actuellement authentifie"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des tickets de l'utilisateur retournee avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<List<TicketResponseDTO>> findMyTickets() {
        List<TicketResponseDTO> tickets = ticketService.findMyTickets(jwtService.getLoggedInUser().getEmail());
        tickets.forEach(this::addTicketLinks);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping
    @Operation(
            summary = "Creer un ticket",
            description = "Cree un nouveau ticket"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Ticket cree avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<TicketResponseDTO> create(@Valid @RequestBody TicketRequestDTO dto) {
        TicketResponseDTO response = ticketService.create(dto, jwtService.getLoggedInUser().getEmail());
        addTicketLinks(response);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Mettre a jour un ticket",
            description = "Modifie un ticket existant a partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket mis a jour avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<TicketResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody TicketUpdateDTO dto) {
        TicketResponseDTO response = ticketService.update(id, dto);
        addTicketLinks(response);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/close")
    @Operation(
            summary = "Fermer un ticket",
            description = "Passe le statut d'un ticket a CLOSED a partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket ferme avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<TicketResponseDTO> close(@PathVariable Long id) {
        TicketResponseDTO response = ticketService.close(id);
        addTicketLinks(response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer un ticket",
            description = "Supprime un ticket a partir de son identifiant"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket supprime avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Map.class)
                    )
            )
    })
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Ticket deleted successfully"));
    }

    @GetMapping("/stats/by-category")
    @Operation(
            summary = "Recuperer les statistiques des tickets par categorie",
            description = "Retourne le nombre de tickets regroupes par categorie"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Statistiques par categorie retournees avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketStatsByCategoryDTO.class)
                    )
            )
    })
    public ResponseEntity<List<TicketStatsByCategoryDTO>> getStatsByCategory() {
        return ResponseEntity.ok(ticketService.getStatsByCategory());
    }

    @GetMapping("/stats/by-status")
    @Operation(
            summary = "Recuperer les statistiques des tickets par statut",
            description = "Retourne le nombre de tickets regroupes par statut"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Statistiques par statut retournees avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TicketStatsByStatusDTO.class)
                    )
            )
    })
    public ResponseEntity<List<TicketStatsByStatusDTO>> getStatsByStatus() {
        return ResponseEntity.ok(ticketService.getStatsByStatus());
    }


    @GetMapping("/agent/{agentId}")
    @Operation(
            summary = "Lister les tickets assignes a un agent",
            description = "Retourne les tickets assignes a un agent. Si showAll=true, les tickets CLOSED sont aussi inclus."
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
    public ResponseEntity<List<AssignedTicketResponseDTO>> getTicketsByAgent(
            @PathVariable Long agentId,
            @RequestParam(defaultValue = "false") boolean showAll
    ) {
        return ResponseEntity.ok(ticketAssignmentService.getTicketsByAgent(agentId, showAll));
    }

    @GetMapping("/me/agent/open")
    @Operation(
            summary = "Lister mes tickets OPEN assignes",
            description = "Retourne les tickets avec statut OPEN assignes a l'agent actuellement authentifie"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des tickets OPEN assignes retournee avec succes",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AssignedTicketResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<List<AssignedTicketResponseDTO>> findMyOpenAssignedTickets() {
        return ResponseEntity.ok(
                ticketAssignmentService.findMyOpenAssignedTickets(
                        jwtService.getLoggedInUser().getRole(),
                        jwtService.getLoggedInUser().getEmail()
                )
        );
    }

    private void addTicketLinks(TicketResponseDTO ticket) {
        Long ticketId = ticket.getId();
        ticket.add(linkTo(methodOn(TicketController.class).findById(ticketId)).withSelfRel());
        ticket.add(linkTo(methodOn(TicketController.class).findAll()).withRel("allTickets"));
        ticket.add(linkTo(methodOn(TicketCommentController.class).findByTicket(ticketId)).withRel("comments"));
        ticket.add(linkTo(methodOn(TicketAssignmentController.class).getAssignmentsByTicket(ticketId)).withRel("assignments"));
    }
}
