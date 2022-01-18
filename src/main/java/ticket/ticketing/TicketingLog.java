package ticket.ticketing;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Member member;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private ShowInfo showInfo;

    private LocalDateTime createdTime;

    @ColumnDefault("false")
    private boolean success;

    @Builder
    public TicketingLog(Member member, ShowInfo showInfo, boolean success) {
        this.member = member;
        this.showInfo = showInfo;
        this.success = success;
        this.createdTime = LocalDateTime.now();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
