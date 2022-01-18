package ticket.ticketing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ticket.entity.TicketingLog;
import ticket.entity.UserEntity;
import ticket.repository.TicketingLogRepository;
import ticket.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TicketingLogTest {
    @Autowired
    TicketingLogRepository ticketingLogRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShowInfoRepository showInfoRepository;

    @AfterEach
    public void clear() throws Exception {
        userRepository.deleteAll();
        ticketingLogRepository.deleteAll();
        showInfoRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createLog() {
        UserEntity u = new UserEntity();
        u.setName("jack");
        u.setCrypted_pwd("999");
        ShowInfo s = ShowInfo.builder().name("SpiderMan").build();


        List<UserEntity> check = userRepository.findAll();
        userRepository.save(u);
        check = userRepository.findAll();

        List<ShowInfo> checkShowInfo = showInfoRepository.findAll();

        showInfoRepository.save(s);

        checkShowInfo = showInfoRepository.findAll();

        TicketingLog log = TicketingLog.builder().user(u).showInfo(s).build();

        ticketingLogRepository.save(log);

        assertThat(ticketingLogRepository.findAll().get(0).getUser().getName()).isEqualTo("jack");
        assertThat(ticketingLogRepository.findAll().get(0).getShowInfo().getName()).isEqualTo("SpiderMan");
    }
}
