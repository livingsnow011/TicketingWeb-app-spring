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

    private Long showInfoId;

    private LocalDateTime showDate;

    @Builder
    public ShowDate(Long showInfoId, LocalDateTime showDate) {
        this.showInfoId = showInfoId;
        this.showDate = showDate;
    }
}
