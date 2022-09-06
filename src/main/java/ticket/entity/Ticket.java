package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "ticket")
@Entity
public class Ticket {

    @Id
    @GeneratedValue
    @Column(name = "ticket_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "show_date_id")
    private ShowDate showDate;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "show_seat_id")
    private ShowSeat showSeat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    Ticket(ShowDate showDate, ShowSeat showSeat){
        this.showDate = showDate;
        this.showSeat = showSeat;
    }

    public void setBook(Book book){
        this.book = book;
    }

}
