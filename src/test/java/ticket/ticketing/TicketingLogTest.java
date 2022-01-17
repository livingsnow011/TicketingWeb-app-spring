package ticket.ticketing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TicketingLogTest {
    @Autowired
    TicketingLogRepository ticketingLogRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ShowInfoRepository showInfoRepository;

    @AfterEach
    public void clear() throws Exception {
        memberRepository.deleteAll();
        ticketingLogRepository.deleteAll();
        showInfoRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createLog() {
        Member m = Member.builder().name("jack").build();
        ShowInfo s = ShowInfo.builder().name("SpiderMan").build();


        List<Member> check = memberRepository.findAll();
        memberRepository.save(m);
        check = memberRepository.findAll();

        List<ShowInfo> checkShowInfo = showInfoRepository.findAll();

        showInfoRepository.save(s);

        checkShowInfo = showInfoRepository.findAll();

        TicketingLog log = TicketingLog.builder().member(m).showInfo(s).build();

        ticketingLogRepository.save(log);

        assertThat(ticketingLogRepository.findAll().get(0).getMember().getName()).isEqualTo("jack");
        assertThat(ticketingLogRepository.findAll().get(0).getShowInfo().getName()).isEqualTo("SpiderMan");
    }
}
