package ticket.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticket.entity.RequestUser;
import ticket.entity.ResponseUser;
import ticket.entity.UserDTO;
import ticket.entity.UserEntity;
import ticket.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
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


    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) throws Exception {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = mapper.map(user, UserDTO.class);

        UserDTO responseDTO = userService.saveUser(userDTO);

        ResponseUser responseUser = mapper.map(responseDTO, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }


    @DeleteMapping("/users/{userId}")
    public Long deleteUser(@PathVariable("userId") Long userId) throws Exception {
        userService.deleteUser(userId);
        return userId;
    }

}
