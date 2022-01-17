package ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticket.entity.UserEntity;
import ticket.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(UserEntity user) throws Exception {
        user.setCrypted_pwd("112");
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) throws Exception {

    }
}
