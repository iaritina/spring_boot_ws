package mg.tpws.restapi.repository;

import mg.tpws.restapi.dto.ticket.TicketStatsByCategoryDTO;
import mg.tpws.restapi.dto.ticket.TicketStatsByStatusDTO;
import mg.tpws.restapi.model.Ticket;
import mg.tpws.restapi.model.TicketStatus;
import mg.tpws.restapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCreator(User creator);


    @Query("""
        SELECT new mg.tpws.restapi.dto.ticket.TicketStatsByCategoryDTO(c.name, COUNT(t))
        FROM Category c
        LEFT JOIN Ticket t ON t.category = c
        GROUP BY c.name
    """)
    List<TicketStatsByCategoryDTO> countTicketsByCategory();

    @Query("""
        SELECT new mg.tpws.restapi.dto.ticket.TicketStatsByStatusDTO(t.status, COUNT(t))
        FROM Ticket t
        GROUP BY t.status
    """)
    List<TicketStatsByStatusDTO> countExistingTicketsByStatus();

    default List<TicketStatsByStatusDTO> countTicketsByStatus() {
        Map<TicketStatus, Long> totalsByStatus = countExistingTicketsByStatus()
                .stream()
                .collect(Collectors.toMap(
                        TicketStatsByStatusDTO::getStatus,
                        TicketStatsByStatusDTO::getTotal,
                        Long::sum
                ));

        return Arrays.stream(TicketStatus.values())
                .map(status -> new TicketStatsByStatusDTO(
                        status,
                        totalsByStatus.getOrDefault(status, 0L)
                ))
                .toList();
    }
}
