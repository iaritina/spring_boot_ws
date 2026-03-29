package mg.tpws.restapi.service;

import mg.tpws.restapi.dto.ticket.*;
import mg.tpws.restapi.model.Category;
import mg.tpws.restapi.model.Ticket;
import mg.tpws.restapi.model.TicketStatus;
import mg.tpws.restapi.model.User;
import mg.tpws.restapi.repository.CategoryRepository;
import mg.tpws.restapi.repository.TicketRepository;
import mg.tpws.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository,
                         UserRepository userRepository,
                         CategoryRepository categoryRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<TicketResponseDTO> findAll() {
        return ticketRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TicketResponseDTO findById(Long id) {
        return toResponseDTO(getTicketOrThrow(id));
    }

    public List<TicketResponseDTO> findMyTickets(String email) {
        User creator = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return ticketRepository.findByCreator(creator)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TicketResponseDTO create(TicketRequestDTO dto, String userEmail) {
        User creator = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        Ticket ticket = Ticket.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .status(TicketStatus.OPEN)
                .creator(creator)
                .category(category)
                .build();

        return toResponseDTO(ticketRepository.save(ticket));
    }

    public TicketResponseDTO update(Long id, TicketUpdateDTO dto) {
        Ticket ticket = getTicketOrThrow(id);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setPriority(dto.getPriority());
        ticket.setStatus(dto.getStatus());
        ticket.setCategory(category);

        return toResponseDTO(ticketRepository.save(ticket));
    }

    public TicketResponseDTO close(Long id) {
        Ticket ticket = getTicketOrThrow(id);

        ticket.setStatus(TicketStatus.CLOSED);

        return toResponseDTO(ticketRepository.save(ticket));
    }

    public void delete(Long id) {
        Ticket ticket = getTicketOrThrow(id);
        ticketRepository.delete(ticket);
    }

    public Ticket getOpenTicketOrThrow(Long id) {
        Ticket ticket = getTicketOrThrow(id);

        if (ticket.getStatus() != TicketStatus.OPEN) {
            throw new RuntimeException("Le ticket est ferme et ne peut plus etre assigne");
        }

        return ticket;
    }

    public List<TicketStatsByCategoryDTO> getStatsByCategory() {
        return ticketRepository.countTicketsByCategory();
    }

    public List<TicketStatsByStatusDTO> getStatsByStatus() {
        return ticketRepository.countTicketsByStatus();
    }

    private Ticket getTicketOrThrow(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
    }

    private TicketResponseDTO toResponseDTO(Ticket ticket) {
        return TicketResponseDTO.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .priority(ticket.getPriority())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .creatorId(ticket.getCreator().getId())
                .creatorName(ticket.getCreator().getName())
                .creatorEmail(ticket.getCreator().getEmail())
                .categoryId(ticket.getCategory().getId())
                .categoryName(ticket.getCategory().getName())
                .build();
    }
}
