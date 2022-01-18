package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticket.entity.TicketingLog;

import java.util.List;
import java.util.Optional;

public interface TicketingLogRepository extends JpaRepository<TicketingLog, Long> {
    List<TicketingLog> findAll();

    Optional<TicketingLog> findById(Long id);
}
