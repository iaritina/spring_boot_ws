package mg.tpws.restapi.service;

import mg.tpws.restapi.dto.ticketComment.CommentRequestDTO;
import mg.tpws.restapi.dto.ticketComment.CommentResponseDTO;
import mg.tpws.restapi.model.Ticket;
import mg.tpws.restapi.model.TicketComment;
import mg.tpws.restapi.model.User;
import mg.tpws.restapi.repository.TicketCommentRepository;
import mg.tpws.restapi.repository.TicketRepository;
import mg.tpws.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketCommentService {

    private final TicketCommentRepository ticketCommentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public TicketCommentService(TicketCommentRepository ticketCommentRepository,
                                TicketRepository ticketRepository,
                                UserRepository userRepository,
                                JwtService jwtService) {
        this.ticketCommentRepository = ticketCommentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public List<CommentResponseDTO> findByTicket(Long ticketId) {
        return ticketCommentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CommentResponseDTO create(Long ticketId, CommentRequestDTO dto, String userEmail) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User author = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TicketComment comment = TicketComment.builder()
                .content(dto.getContent())
                .ticket(ticket)
                .author(author)
                .build();

        return toResponseDTO(ticketCommentRepository.save(comment));
    }

    public void deleteComment(Long ticketId, Long commentId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        TicketComment ticketComment = ticketCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Ticket comment not found"));

        if (!ticketComment.getTicket().getId().equals(ticket.getId())) {
            throw new RuntimeException("This comment does not belong to this ticket");
        }

        String connectedUserEmail = jwtService.getLoggedInUser().getEmail();
        String connectedUserRole = jwtService.getLoggedInUser().getRole();

        boolean isAdmin = "ROLE_ADMIN".equals(connectedUserRole);
        boolean isOwner = ticketComment.getAuthor() != null
                && ticketComment.getAuthor().getEmail().equals(connectedUserEmail);

        if (!isAdmin && !isOwner) {
            throw new RuntimeException("You are not allowed to delete this comment");
        }

        ticketCommentRepository.delete(ticketComment);
    }

    private CommentResponseDTO toResponseDTO(TicketComment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .ticketId(comment.getTicket().getId())
                .authorId(comment.getAuthor().getId())
                .authorName(comment.getAuthor().getName())
                .authorEmail(comment.getAuthor().getEmail())
                .build();
    }
}