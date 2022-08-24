package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticket.entity.BookShow;

public interface BookShowRepository extends JpaRepository<BookShow,Long> {
}
