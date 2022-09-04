package ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticket.entity.Show;
import ticket.entity.ShowSeat;
import ticket.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    //ticket id를 통해 showId를 찾는 메서드
    @Query("select ss.show.id from ShowSeat ss where ss.id in (select t.showSeat.id from Ticket t where t.id=:id)")
    Long findShowIdByTicketId(@Param("id") Long ticketId);

    //추첨에서 사용할 메서드
    @Query("select t from Ticket t where t.showDate.id=:showDateId")
    Optional<List<Ticket>> findTicketsByShowDateId(@Param("showDateId") Long showDateId);

}
