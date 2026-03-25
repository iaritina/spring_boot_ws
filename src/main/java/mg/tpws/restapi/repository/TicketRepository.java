package mg.tpws.restapi.repository;

import mg.tpws.restapi.dto.ticket.TicketStatsByCategoryDTO;
import mg.tpws.restapi.dto.ticket.TicketStatsByStatusDTO;
import mg.tpws.restapi.model.Ticket;
import mg.tpws.restapi.model.TicketPriority;
import mg.tpws.restapi.model.TicketStatus;
import mg.tpws.restapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCreator(User creator);

    List<Ticket> findByStatus(TicketStatus status);

    List<Ticket> findByPriority(TicketPriority priority);

    List<Ticket> findByCategoryId(Long categoryId);

    @Query("""
        SELECT new mg.tpws.restapi.dto.ticket.TicketStatsByCategoryDTO(t.category.name, COUNT(t))
        FROM Ticket t
        GROUP BY t.category.name
    """)
    List<TicketStatsByCategoryDTO> countTicketsByCategory();

    @Query("""
        SELECT new mg.tpws.restapi.dto.ticket.TicketStatsByStatusDTO(t.status, COUNT(t))
        FROM Ticket t
        GROUP BY t.status
    """)
    List<TicketStatsByStatusDTO> countTicketsByStatus();
}
