package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class TicketingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    private LocalDateTime createdTime;

    @ColumnDefault("false")
    private boolean success;

    @Builder
    public TicketingLog(Long userId, Seat seat, boolean success) {
        this.userId = userId;
        this.seat = seat;
        this.success = success;
        this.createdTime = LocalDateTime.now();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
