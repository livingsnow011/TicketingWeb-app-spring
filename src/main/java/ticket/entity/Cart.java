package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ToString
@NoArgsConstructor
@Getter
@Table(name = "cart")
@Entity
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    private User user;

    @Builder
    Cart(User user){
        this.user = user;
    }

}
