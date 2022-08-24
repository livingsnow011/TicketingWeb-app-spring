package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticket.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
