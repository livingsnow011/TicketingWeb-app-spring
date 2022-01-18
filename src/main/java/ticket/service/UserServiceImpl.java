package ticket.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ticket.entity.UserDTO;
import ticket.entity.UserEntity;
import ticket.repository.UserRepository;

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
        userEntity.setCrypted_pwd(passwordEncoder.encode(userDTO.getPwd()));

        userRepository.save(userEntity);

        return mapper.map(userEntity, UserDTO.class);
    }

    @Override
    public UserDTO getUsersByUserId(Long userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDTO userDto = new ModelMapper().map(userEntity, UserDTO.class);
        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUsersByAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(long userId) throws Exception {
        userRepository.deleteById(userId);
    }
}