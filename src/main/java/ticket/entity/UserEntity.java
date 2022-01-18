package ticket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String crypted_pwd;

    @Column(nullable = false)
    private LocalDate created_date = LocalDate.now();

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int current_point;

    /*
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation")
    private Collection<ReservationDto> ticketing_list;

    @OneToMany@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation")
    private Collection<ReservationDto> ticketing_success;

     */
}
