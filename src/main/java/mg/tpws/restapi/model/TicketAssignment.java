package mg.tpws.restapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_assignments")
@Getter
@Setter
public class TicketAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    // ici agent = un User ayant le rôle AGENT
    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @PrePersist
    public void prePersist() {
        assignedAt = LocalDateTime.now();
    }
}
