package ticket.service;

import ticket.entity.RequestLogin;
import ticket.entity.UserDTO;
import ticket.entity.UserEntity;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO) throws Exception;

    UserDTO changeUser(Long userId, UserDTO userDTO) throws Exception;

    UserDTO getUsersByUserId(Long userId);

    UserDTO getUsersById(String id);

    Iterable<UserEntity> getUsersByAll();

    void deleteUser(long userId) throws Exception;

    UserEntity login(String id, String pwd);


}
