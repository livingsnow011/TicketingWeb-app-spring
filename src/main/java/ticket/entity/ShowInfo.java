package ticket.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class ShowInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String classification;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    private List<LocalDateTime> startDate;

    @OneToMany(mappedBy = "showInfo", cascade = CascadeType.REMOVE)
    private List<Seat> seatList;

    private String description;


    @Builder
    public ShowInfo(String name, String classification, List<LocalDateTime> startDate, String description) {
        this.name = name;
        this.classification = classification;
        this.startDate = startDate;
        this.description = description;
    }

    public void updateDateList(List<LocalDateTime> startDate) {
        this.startDate.addAll(startDate);
    }

    public void updateDateList(LocalDateTime startDate) {
        this.startDate.add(startDate);
    }
}
