package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ShowDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="show_info_id")
    private ShowInfo showInfo;

    private LocalDateTime showDate;

    @Builder
    public ShowDate(ShowInfo showInfo, LocalDateTime showDate) {
        this.showInfo = showInfo;
        this.showDate = showDate;
    }
}
