package ticket.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ticket.dto.SignUpFormDto;
import ticket.entity.User;
import ticket.service.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(String userId, String password){
        SignUpFormDto signUpFormDto = SignUpFormDto.builder().id(userId).name("박한솔").password(password).build();
        User user = User.createUser(signUpFormDto, passwordEncoder);
        return userService.saveUser(user);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception{
        String userId = "sju01132";
        String password = "q1w2e3r4";
        this.createUser(userId, password);
        mockMvc.perform(formLogin().userParameter("userId")
                        .loginProcessingUrl("/users/login")
                        .user(userId).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{
        String userId = "sju01132";
        String password = "q1w2e3r4";
        this.createUser(userId, password);
        mockMvc.perform(formLogin().userParameter("userId")
                        .loginProcessingUrl("/users/login")
                        .user(userId).password("12344321"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

}