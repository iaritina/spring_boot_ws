package mg.tpws.restapi.config;

import lombok.RequiredArgsConstructor;
import mg.tpws.restapi.dto.user.RegisterDTO;
import mg.tpws.restapi.model.Category;
import mg.tpws.restapi.model.RoleName;
import mg.tpws.restapi.model.Ticket;
import mg.tpws.restapi.model.TicketPriority;
import mg.tpws.restapi.model.TicketStatus;
import mg.tpws.restapi.model.User;
import mg.tpws.restapi.repository.CategoryRepository;
import mg.tpws.restapi.repository.TicketRepository;
import mg.tpws.restapi.repository.UserRepository;
import mg.tpws.restapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInit {
    @Autowired
    private final AuthService service;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final TicketRepository ticketRepository;

    @Bean
    CommandLineRunner initUsers() {
        return args -> {
            if (userRepository.count() == 0) {
                service.register(new RegisterDTO(
                        "admin@example.com",
                        "Admin",
                        "admin123",
                        RoleName.ROLE_ADMIN
                ));

                service.register(new RegisterDTO(
                        "agent@example.com",
                        "Agent",
                        "agent123",
                        RoleName.ROLE_AGENT
                ));

                service.register(new RegisterDTO(
                        "user@example.com",
                        "User",
                        "user123",
                        RoleName.ROLE_USER
                ));
            }

            if (ticketRepository.count() == 0) {
                Category supportCategory = getOrCreateCategory(
                        "Support technique",
                        "Demandes d'assistance technique"
                );
                Category billingCategory = getOrCreateCategory(
                        "Facturation",
                        "Questions et incidents lies a la facturation"
                );

                User creator = userRepository.findUserByEmail("user@example.com")
                        .orElseThrow(() -> new RuntimeException("Utilisateur de depart introuvable"));

                Ticket openTicket = Ticket.builder()
                        .title("Impossible de se connecter")
                        .description("L'utilisateur ne parvient pas a acceder a son compte.")
                        .priority(TicketPriority.HIGH)
                        .status(TicketStatus.OPEN)
                        .creator(creator)
                        .category(supportCategory)
                        .build();

                Ticket closedTicket = Ticket.builder()
                        .title("Demande de facture corrigee")
                        .description("La demande a ete traitee et le ticket peut rester clos.")
                        .priority(TicketPriority.MEDIUM)
                        .status(TicketStatus.CLOSED)
                        .creator(creator)
                        .category(billingCategory)
                        .build();

                ticketRepository.save(openTicket);
                ticketRepository.save(closedTicket);
            }
        };
    }

    private Category getOrCreateCategory(String name, String description) {
        return categoryRepository.findByName(name)
                .orElseGet(() -> {
                    Category category = new Category();
                    category.setName(name);
                    category.setDescription(description);
                    return categoryRepository.save(category);
                });
    }
}
