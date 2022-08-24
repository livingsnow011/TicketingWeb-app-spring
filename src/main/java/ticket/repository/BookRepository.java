package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticket.entity.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
