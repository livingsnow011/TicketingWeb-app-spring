package ticket.ticketing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAll();

    Optional<Member> findById(Long id);

    List<Member> findByName(String name);
}
