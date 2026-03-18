package mg.tpws.restapi.service;

import mg.tpws.restapi.dto.ticketAssignment.AssignedTicketResponseDTO;
import mg.tpws.restapi.dto.ticketAssignment.TicketAssignmentDetailResponseDTO;
import mg.tpws.restapi.dto.ticketAssignment.TicketAssignmentResponseDTO;
import mg.tpws.restapi.model.Ticket;
import mg.tpws.restapi.model.TicketAssignment;
import mg.tpws.restapi.model.User;
import mg.tpws.restapi.repository.TicketAssignmentRepository;
import mg.tpws.restapi.repository.TicketRepository;
import mg.tpws.restapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketAssignmentService {

    private final TicketAssignmentRepository ticketAssignmentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketAssignmentService(
            TicketAssignmentRepository ticketAssignmentRepository,
            TicketRepository ticketRepository,
            UserRepository userRepository
    ) {
        this.ticketAssignmentRepository = ticketAssignmentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public TicketAssignmentResponseDTO assignTicketToAgent(Long ticketId, Long agentId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket introuvable"));

        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (agent.getRole() == null || !agent.getRole().name().equals("AGENT")) {
            throw new RuntimeException("Cet utilisateur n'est pas un agent");
        }

        boolean alreadyAssigned = ticketAssignmentRepository.existsByTicketIdAndAgentId(ticketId, agentId);
        if (alreadyAssigned) {
            throw new RuntimeException("Ce ticket est déjà assigné à cet agent");
        }

        TicketAssignment assignment = new TicketAssignment();
        assignment.setTicket(ticket);
        assignment.setAgent(agent);

        TicketAssignment savedAssignment = ticketAssignmentRepository.save(assignment);

        return toTicketAssignmentResponseDTO(savedAssignment);
    }

    public List<TicketAssignmentDetailResponseDTO> getAssignmentsByTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket introuvable"));

        return ticketAssignmentRepository.findByTicketId(ticket.getId())
                .stream()
                .map(this::toTicketAssignmentDetailResponseDTO)
                .toList();
    }

    public List<AssignedTicketResponseDTO> getTicketsByAgent(Long agentId) {
        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (agent.getRole() == null || !agent.getRole().name().equals("AGENT")) {
            throw new RuntimeException("Cet utilisateur n'est pas un agent");
        }

        return ticketAssignmentRepository.findByAgentId(agentId)
                .stream()
                .map(this::toAssignedTicketResponseDTO)
                .toList();
    }

    private TicketAssignmentResponseDTO toTicketAssignmentResponseDTO(TicketAssignment assignment) {
        return TicketAssignmentResponseDTO.builder()
                .id(assignment.getId())
                .assignedAt(assignment.getAssignedAt())
                .ticketId(assignment.getTicket().getId())
                .agentId(assignment.getAgent().getId())
                .build();
    }

    private AssignedTicketResponseDTO toAssignedTicketResponseDTO(TicketAssignment assignment) {
        Ticket ticket = assignment.getTicket();

        return AssignedTicketResponseDTO.builder()
                .assignmentId(assignment.getId())
                .assignedAt(assignment.getAssignedAt())
                .ticketId(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .priority(ticket.getPriority())
                .build();
    }

    private TicketAssignmentDetailResponseDTO toTicketAssignmentDetailResponseDTO(TicketAssignment assignment) {
        return TicketAssignmentDetailResponseDTO.builder()
                .id(assignment.getId())
                .assignedAt(assignment.getAssignedAt())
                .ticketId(assignment.getTicket().getId())
                .ticketTitle(assignment.getTicket().getTitle())
                .ticketDescription(assignment.getTicket().getDescription())
                .ticketStatus(assignment.getTicket().getStatus())
                .ticketPriority(assignment.getTicket().getPriority())
                .agentId(assignment.getAgent().getId())
                .agentName(assignment.getAgent().getName())
                .agentEmail(assignment.getAgent().getEmail())
                .build();
    }
}
;