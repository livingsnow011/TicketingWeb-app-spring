package ticket.seat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="seats")
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="member_id")
    private Long seatId;

    //@ManyToOne
    //@JoinColumn(name="show_id")
    @Column(name="show_id")
    private Long showId;

    @Column(name="show_date")
    private Date showDate;

    private String grade;

    private int price;

    @Column(name = "total_seat")
    private int totalSeat;

    @Column(name="current_count")
    private int currentCount;

}
