package ticket.service;

import ticket.entity.UserDTO;
import ticket.entity.UserEntity;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO) throws Exception;

    UserDTO getUsersByUserId(String userId);

    Iterable<UserEntity> getUsersByAll();

    void deleteUser(long userId) throws Exception;


}
