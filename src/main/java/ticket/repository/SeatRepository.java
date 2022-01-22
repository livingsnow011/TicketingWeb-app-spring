package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ticket.entity.Seat;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByShowInfoId(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM seat WHERE show_date between current_timestamp AND date_add(current_timestamp, interval 1 day)")
    List<Seat> findLotteryTarget();

    @Query("SELECT s FROM Seat s WHERE show_date > current_timestamp")
    List<Seat> findRefundTarget();
}
