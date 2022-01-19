package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime showDate;

    private String grade;

    private int price;

    @Column(name = "total_seat")
    private int totalSeat;

    @Column(name="current_count")
    private int currentCount = 0;

    @Builder
    public Seat(ShowInfo showInfo, LocalDateTime showDate, String grade, int price, int totalSeat) {
        this.showInfo = showInfo;
        this.showDate = showDate;
        this.grade = grade;
        this.price = price;
        this.totalSeat = totalSeat;
    }

    public void addCurrentCount(int num) {
        this.currentCount += num;
    }
}
