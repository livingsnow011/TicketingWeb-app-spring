package ticket.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ticket.dto.SignUpFormDto;
import ticket.entity.User;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(){
        SignUpFormDto signUpFormDto = SignUpFormDto.builder().id("sju01132").name("박한솔").password("q1w2e3r4").build();
        return User.createUser(signUpFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원 가입 테스트")
    public void saveUserTest(){
        User user = createUser();

        User savedUser = userService.saveUser(user);

        assertEquals(user.getUserId(),savedUser.getUserId());
        assertEquals(user.getName(),savedUser.getName());
        assertEquals(user.getPassword(),savedUser.getPassword());
        assertEquals(user.getRole(),savedUser.getRole());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateUserTest(){
        User user1 = createUser();
        User user2 = createUser();

        userService.saveUser(user1);

        Throwable e = assertThrows(IllegalStateException.class,()->{
            userService.saveUser(user2);
        });

        assertEquals("중복된 아이디가 있습니다.",e.getMessage());
    }

}