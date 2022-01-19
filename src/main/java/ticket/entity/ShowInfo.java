package ticket.entity;

import lombok.*;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "showInfo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Seat> seatList;

    private String description;


    @Builder
    public ShowInfo(String name, String classification, String description) {
        this.name = name;
        this.classification = classification;
        this.description = description;
    }
}
