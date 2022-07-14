package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import ticket.constant.Role;

import javax.persistence.*;

//setter 사용 지양, Role을 enum으로 대체
//@Builder 추가
@NoArgsConstructor  @Getter
@DynamicInsert @DynamicUpdate
@Table(name = "ruser")
@Entity
public class RUser {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "integer default 0")
    private Integer currentPoint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public RUser(String userId,String name,String password,Role role){
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.role = role;
    }

}
