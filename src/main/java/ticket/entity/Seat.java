package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="show_info_id")
    private ShowInfo showInfo;

    private String grade;

    private int price;

    @Column(name = "total_seat")
    private int totalSeat;

    @Column(name="current_count")
    private int currentCount = 0;

    @Builder
    public Seat(ShowInfo showInfo, String grade, int price, int totalSeat) {
        this.showInfo = showInfo;
        this.grade = grade;
        this.price = price;
        this.totalSeat = totalSeat;
    }
}
