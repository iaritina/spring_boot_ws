package mg.tpws.restapi.repository;

import mg.tpws.restapi.model.TicketAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketAssignmentRepository extends JpaRepository<TicketAssignment, Long> {
    boolean existsByTicketIdAndAgentId(Long ticketId, Long agentId);
    List<TicketAssignment> findByAgentId(Long agentId);
    List<TicketAssignment> findByTicketId(Long ticketId);
}
