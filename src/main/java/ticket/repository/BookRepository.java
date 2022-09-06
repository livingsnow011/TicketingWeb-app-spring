package ticket.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticket.entity.Book;
import ticket.entity.User;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("select b from Book b " +
            "where b.user.userId = :userId " +
            "order by b.bookDate desc")
    List<Book> findBooks(@Param("userId") String userId, Pageable pageable);

    @Query("select count(b) from Book b " +
            "where b.user.userId = :userId")
    Long countBook(@Param("userId") String userId);

    @Query("select b.user from Book b where b.id=:id")
    User findUserById(@Param("id") Long id);

}
