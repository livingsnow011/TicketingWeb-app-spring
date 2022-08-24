package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticket.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
