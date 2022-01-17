package ticket.ticketing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketingLogRepository extends JpaRepository<TicketingLog, Long> {
    List<TicketingLog> findAll();

    Optional<TicketingLog> findById(Long id);

    List<TicketingLog> findByMember(Member member) throws Exception;

    List<TicketingLog> findByShowInfo(ShowInfo showInfo) throws Exception;
}
