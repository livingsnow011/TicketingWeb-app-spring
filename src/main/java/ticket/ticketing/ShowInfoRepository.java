package ticket.ticketing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowInfoRepository extends JpaRepository<ShowInfo, Long> {
    List<ShowInfo> findAll();

    Optional<ShowInfo> findById(Long id);
}
