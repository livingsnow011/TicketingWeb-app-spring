package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticket.entity.Show;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show,Long> {
    List<Show> findAll();

    Optional<Show> findById(Long id);

    List<Show> findByShowName(String showName);

    @Modifying
    @Query("delete from Show show where show.id =:id")
    void deleteByIdInQuery(@Param("id") Long showId);

//    List<Show> findByShowNameOrAndShowDetail(String showName, String showDetail);
}
