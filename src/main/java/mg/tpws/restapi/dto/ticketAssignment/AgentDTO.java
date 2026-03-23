package mg.tpws.restapi.dto.ticketAssignment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AgentDTO {
    private Long id;
    private String name;
    private String email;
}