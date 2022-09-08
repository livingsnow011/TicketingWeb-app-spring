package ticket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ticket.dto.SignUpFormDto;
import ticket.entity.User;
import ticket.service.UserService;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value="/new")
    public String signUpForm(Model model){
        model.addAttribute("signUpFormDto",new SignUpFormDto());
        return "user/userForm";
    }

    @PostMapping(value = "/new")
    public String signUpForm(@Valid SignUpFormDto signUpFormDto, BindingResult bindingResult,Model model){

        if(bindingResult.hasErrors()){
            return "user/userForm";
        }

        try{
            User user = User.createUser(signUpFormDto, passwordEncoder);
            userService.saveUser(user);
            log.info("사용자 {} 회원가입", user.getUserId());
        } catch (IllegalStateException e){
            log.error("회원 가입 도중 에러 발생");
            model.addAttribute("errorMessage", e.getMessage());
            return "user/userForm";
        }
        return "redirect:/";
    }

    @GetMapping(value="/login")
    public String loginUser(){
        return "user/loginForm";
    }

    @GetMapping(value="/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/user/loginForm";
    }
}
