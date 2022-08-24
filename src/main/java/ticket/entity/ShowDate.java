package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "show_date")
@Entity
public class ShowDate {

    @Id
    @Column(name = "show_date_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime showDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "show_id")
    private Show show;

    @Builder
    public ShowDate(LocalDateTime showDate,Show show){
        this.showDate = showDate;
        this.show = show;
    }

    public void updateShowDate(LocalDateTime showDate){
        this.showDate = showDate;
    }
}
