package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ticket.constant.Role;
import ticket.dto.SignUpFormDto;
import ticket.exception.OutOfPointException;

import javax.persistence.*;

@NoArgsConstructor  @Getter
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
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
    public User(String userId,String name,String password,Role role,Integer currentPoint){
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.currentPoint = currentPoint;
    }

    public static User createUser(SignUpFormDto signUpFormDto, PasswordEncoder passwordEncoder){
        User user = User.builder().
                userId(signUpFormDto.getId()).
                name(signUpFormDto.getName()).
                password(passwordEncoder.encode(signUpFormDto.getPassword())).
                role(Role.ADMIN).
                currentPoint(100000).
                build();

        return user;
    }

    public void usePoint(Integer showPoint){
        int point = this.currentPoint - showPoint;
        if(point<0){
            throw new OutOfPointException("사용자의 포인트가 부족합니다. (현재 포인트 : " + this.currentPoint + ")");
        }
        this.currentPoint = point;
    }

    public void refundPoint(Integer showPoint){
        this.currentPoint += showPoint;
    }

}
