package ticket.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ticket.entity.*;
import ticket.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userEntityList = userService.getUsersByAll();
        List<ResponseUser> responseUserList = new ArrayList<>();
        userEntityList.forEach(listItem -> {
            responseUserList.add(new ModelMapper().map(listItem, ResponseUser.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(responseUserList);
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUsers(@PathVariable("userId") Long userId) {
        UserDTO userDTO = userService.getUsersByUserId(userId);
        ResponseUser responseUser = new ModelMapper().map(userDTO, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @GetMapping("/users/searchId/{id}")
    public ResponseEntity<ResponseUser> getUsers(@PathVariable("id") String id) {
        UserDTO userDTO = userService.getUsersById(id);
        System.out.println(userDTO);
        ResponseUser responseUser = new ModelMapper().map(userDTO, ResponseUser.class);
        System.out.println(responseUser);
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }


    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) throws Exception {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO userDTO = userService.saveUser(mapper.map(user, UserDTO.class));

        ResponseUser responseUser = mapper.map(userDTO, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }


    @DeleteMapping("/users/{userId}")
    public Long deleteUser(@PathVariable("userId") Long userId) throws Exception {
        userService.deleteUser(userId);
        return userId;
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> changeUser(@PathVariable("userId") Long userId, @RequestBody UserDTO userDTO) throws Exception {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO responseDTO = userService.changeUser(userId, userDTO);

        ResponseUser responseUser = mapper.map(responseDTO, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @PostMapping("/login")
    public String login(@RequestBody RequestLogin requestLogin, HttpServletResponse response, @CookieValue(name = "userId", required = false) Long userId) {
        //ResponseEntity<ResponseUser> login(@RequestBody RequestLogin requestLogin, HttpServletResponse response, @CookieValue(name = "userId", required = false) Long userId) {
        if (userId != null) {
            return "redirect:http://localhost:8080/";
        }

        UserEntity userEntity = userService.login(requestLogin.getId(), requestLogin.getPwd());
        if (userEntity == null) {
            return null;
        }
        Cookie userIdCookie = new Cookie("userId", String.valueOf(userEntity.getUserId()));
        response.addCookie(userIdCookie);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Set-Cookie", "userId" + String.valueOf(userEntity.getUserId()));

//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.COOKIE, "userId" + "=" + userEntity.getUserId());
//        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
        ModelMapper mapper = new ModelMapper();
        ResponseUser responseUser = mapper.map(userEntity, ResponseUser.class);
        return "redirect:http://localhost:8080/";
        //return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
