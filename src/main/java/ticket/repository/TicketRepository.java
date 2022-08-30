package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticket.entity.Show;
import ticket.entity.ShowSeat;
import ticket.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Long> {


    @Query("select ss from ShowSeat ss join Ticket t where t.showSeat.id = ss.id and t.id=:id")
    ShowSeat findShowByTicketId(@Param("id") Long ticketId);
}
