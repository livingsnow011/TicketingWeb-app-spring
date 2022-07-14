package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ticket.entity.TicketingLog;

import java.util.List;
import java.util.Optional;

public interface TicketingLogRepository extends JpaRepository<TicketingLog, Long> {
    List<TicketingLog> findAll();

    Optional<TicketingLog> findById(Long id);

    List<TicketingLog> findBySeatId(Long id);

    List<TicketingLog> findByUserId(Long id);

    @Query("SELECT t FROM TicketingLog t where seat_id = ?1 and success = FALSE")
    List<TicketingLog> findLotteryTargetBySeatId(Long id);

    @Query("SELECT t FROM TicketingLog t where seat_id = ?1 and success = FALSE AND refunded = FALSE")
    List<TicketingLog> findRefundTargetBySeatId(Long id);
}
