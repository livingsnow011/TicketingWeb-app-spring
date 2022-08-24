package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticket.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserId(String userId);
}
