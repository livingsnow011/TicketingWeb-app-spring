package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticket.entity.Show;
import ticket.entity.ShowSeat;
import ticket.entity.Ticket;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Query("select ss.show.id from ShowSeat ss where ss.id in (select t.showSeat.id from Ticket t where t.id=:id)")
    Long findShowIdByTicketId(@Param("id") Long ticketId);
}
