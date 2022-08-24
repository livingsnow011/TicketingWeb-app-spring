package ticket.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ticket.constant.Classification;
import ticket.entity.Show;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class ShowRepositoryTest {

    @Autowired
    ShowRepository showRepository;

    @Test
    @DisplayName("show 저장 테스트")
    public void createShowTest(){
        Show show = Show.builder().showName("공연 1").classification(Classification.MOVIE).showDetail("공연 1 상세").build();
        Show savedShow = showRepository.save(show);
        System.out.println(savedShow);
    }

    @Test
    @DisplayName("show name 조회 테스트")
    public void findByShowNameTest(){
        this.createShowLIst();
        List<Show> showList = showRepository.findByShowName("공연 7");

        for (Show show : showList){
            System.out.println(show);
        }
    }

    public void createShowLIst(){
        for (int i = 1; i <100 ; i++) {
            Show show = Show.builder().showName("공연 "+i).classification(Classification.MOVIE).showDetail("공연 "+i+" 상세").build();
            showRepository.save(show);
        }
    }
}