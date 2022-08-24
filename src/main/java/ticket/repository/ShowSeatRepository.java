package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticket.entity.Show;
import ticket.entity.ShowSeat;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
    List<ShowSeat> findByShowIdOrderByIdAsc(Long showId);

    List<ShowSeat> findByShowId(Long showId);

    @Modifying
    @Query("delete from ShowSeat ss where ss.show=:show")
    void deleteAllByShowIdInQuery(@Param("show") Show show);
}
