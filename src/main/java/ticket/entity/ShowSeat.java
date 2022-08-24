package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "show_seat")
@Entity
public class ShowSeat {

    @Id
    @Column(name = "show_seat_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "seat_grade")
    private String seatGrade;

    @Column(name = "seat_count")
    private Integer seatCount;

    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "show_id")
    private Show show;

    @Builder
    public ShowSeat(String seatGrade,Integer seatCount,Integer price,Show show){
        this.seatGrade = seatGrade;
        this.seatCount = seatCount;
        this.price = price;
        this.show = show;
    }

    public void updateShowSeat(String seatGrade, Integer seatCount, Integer price){
        this.seatGrade = seatGrade;
        this.seatCount = seatCount;
        this.price = price;
    }
}
