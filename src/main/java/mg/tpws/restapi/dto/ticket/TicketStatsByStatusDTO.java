package mg.tpws.restapi.dto.ticket;

import mg.tpws.restapi.model.TicketStatus;

public class TicketStatsByStatusDTO {

    private TicketStatus status;
    private Long total;

    public TicketStatsByStatusDTO(TicketStatus status, Long total) {
        this.status = status;
        this.total = total;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public Long getTotal() {
        return total;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
