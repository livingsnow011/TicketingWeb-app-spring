package ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ticket.entity.UserEntity;
import ticket.service.UserService;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public String createUser(@RequestParam String name) throws Exception{
        UserEntity user = new UserEntity();
        user.setName(name);
        userService.saveUser(user);
        return "200";
    }
}
