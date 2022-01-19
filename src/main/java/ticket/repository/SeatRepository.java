package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ticket.entity.Seat;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByShowInfoId(Long id);

    @Query("SELECT s FROM Seat s WHERE show_date = ?1 AND show_info_id = ?2")
    List<Seat> findLotterySeatByTimeAndShowInfoId(LocalDateTime time, Long id);
}
