package ticket.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ticket.entity.RequestLogin;
import ticket.entity.UserDTO;
import ticket.entity.UserEntity;
import ticket.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO saveUser(UserDTO userDTO) throws Exception {
        //userDTO.setUser_id(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        System.out.println(userEntity);
        userEntity.setCrypted_pwd(passwordEncoder.encode(userDTO.getPwd()));

        userRepository.save(userEntity);
        System.out.println(userEntity);
        return mapper.map(userRepository.findByUserId(userEntity.getUserId()), UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO changeUser(Long userId, UserDTO userDTO) throws Exception {
        ModelMapper mapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);

        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(userDTO, userEntity);


        return mapper.map(userEntity, UserDTO.class);
    }

    @Override
    public UserDTO getUsersByUserId(Long userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDTO userDTO = new ModelMapper().map(userEntity, UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO getUsersById(String id) {
        UserEntity userEntity = userRepository.findByMyId(id);
        System.out.println(userEntity);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDTO userDTO = new ModelMapper().map(userEntity, UserDTO.class);
        return userDTO;
    }

    @Override
    public Iterable<UserEntity> getUsersByAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(long userId) throws Exception {
        userRepository.deleteById(userId);
    }

    @Override
    public UserEntity login(String id, String pwd) {
        UserEntity userEntity = userRepository.findById(id);
        System.out.println(userEntity);
        if (userEntity.getCrypted_pwd().equals(passwordEncoder.encode(pwd))){
            return userEntity;
        } else {
            return null;
        }

    }
}