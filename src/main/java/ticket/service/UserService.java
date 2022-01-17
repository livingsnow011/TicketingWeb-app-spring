package ticket.service;

import ticket.entity.UserEntity;

public interface UserService {
    void saveUser(UserEntity user) throws Exception;

    void deleteUser(long userId) throws Exception;
}
