package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="show_info_id")
    private ShowInfo showInfo;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<TicketingLog> ticketingLogList;

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
