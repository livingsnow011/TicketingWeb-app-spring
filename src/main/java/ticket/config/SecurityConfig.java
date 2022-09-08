package ticket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ticket.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserService userService;
     //시큐리티에서 filterChain 메서드를 통해 구현
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                //로그인 페이지 url 설정
                .loginPage("/users/login")
                //로그인 성공시 이동할 url 설정
                .defaultSuccessUrl("/")
                //로그인 시 사용할 파라미터
                .usernameParameter("userId")
                //실패시 이동할 url 설정
                .failureUrl("/users/login/error")
                .and()
                .logout()
                //로그아웃 url 설정
                .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
                //로그 아웃 시 이동할 url 설정
                .logoutSuccessUrl("/");

        //시큐리티 처리에 HttpServletRequest를 사용
        http.authorizeRequests()
                //.permitAll() 이것들을 허용하겠습니다.
                // static 디렉토리 하위 파일들은 인증을 무시
                .mvcMatchers("/css/**", "/js/**", "/img/**","/images/**").permitAll()
                // 아래와 같은 경로들은 인증을 무시
                .mvcMatchers("/", "/users/**","/show/**","/showDtl/**").permitAll()
                // /admin으로 시작하는 경로는 ADMIN role일 경우에만 접근 가능하도록 설정합니다.
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                //나머지 경로들은 인증을 요구합니다.
                .anyRequest().authenticated();

        //인증되지 않는 사용자가 리소스에 접근하였을 때 수행되는 핸들러를 등록
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
