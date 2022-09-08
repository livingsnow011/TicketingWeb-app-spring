package ticket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.dto.UserHistDto;
import ticket.entity.User;
import ticket.repository.UserRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public User saveUser(User user){
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    //유저 아이디 중복 검사
    private void validateDuplicateUser(User user){
        User findUser = userRepository.findByUserId(user.getUserId());
        if(findUser!=null){
            log.error("중복된 아이디 {} ",findUser.getUserId());
            throw new IllegalStateException("중복된 아이디가 있습니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId);

        if(user == null){
            throw new UsernameNotFoundException(userId);
        }

        //이럴줄 몰랐음....
        //다음에는 user 말고 member 로 지어라
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }

    public UserHistDto getUserHist(String userId){
        User user = userRepository.findByUserId(userId);
        return UserHistDto.of(user);
    }
}
