package ticket.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
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

    @Column(columnDefinition = "integer default 0")
    private Integer current_point;

    @Column(columnDefinition = "varchar(255) default 'user'")
    private String role;

    /*
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation")
    private Collection<ReservationDto> ticketing_list;

    @OneToMany@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation")
    private Collection<ReservationDto> ticketing_success;

     */
}
