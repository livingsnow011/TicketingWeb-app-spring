package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ticket.entity.ShowDate;

import java.util.List;

public interface ShowDateRepository extends JpaRepository<ShowDate, Long> {
    @Query("SELECT s FROM ShowDate s WHERE show_info_id = ?1")
    List<ShowDate> findByShowInfoId(Long id);
}
