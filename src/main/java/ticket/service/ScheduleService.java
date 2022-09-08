package ticket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ticket.entity.ShowDate;
import ticket.repository.ShowDateRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ShowDateRepository showDateRepository;
    private final LotteryService lotteryService;

    //매일 정오에 3일 안에 있는 showDate를 조회
    //개발 환경에서는 매시간 30분 단위로
    @Scheduled(cron = "0 30 * * * ?")
    public void test(){
        LocalDateTime now = LocalDateTime.now();
        log.info("현재 시간 {} 예매 cron 수행 ", now);

        // 예매처리가 되지 않고, 지금보다 미래의 공연 날짜들을 모두 가져옴
        List<ShowDate> showDateList = showDateRepository.findShowDateByNow(now).orElseThrow(EntityNotFoundException::new);
        Iterator<ShowDate> iterator = showDateList.iterator();
        //메소드가 실행되고 3일 안까지 공연 날짜로 리스트를 줄임
        LocalDateTime afterThreeDays = now.plusDays(3);

        // 동시성 에러
        // for (ShowDate showDate : showDateList) {
        //  now.plusDays(3);
        //  if(showDate.getShowDate().isAfter(now)){
        //      showDateList.remove(showDate);
        //  }
        // }


        List<Long> targetShowDateIds = new ArrayList<>();
        //iterator 사용, 동작은 하나 멀티 쓰레드 환경에서 성능이슈가 있다고함
        while(iterator.hasNext()){
            ShowDate showDate = iterator.next();
            //3일 후보다 먼 공연이라면
            if(showDate.getShowDate().isAfter(afterThreeDays)){
                //제거
                iterator.remove();
            }else{
                targetShowDateIds.add(showDate.getId());
            }
        }

        if(!targetShowDateIds.isEmpty()){
            log.info("확정된 날짜 {} 개 추첨 서비스 실행",targetShowDateIds.size());
            lotteryService.doLottery(targetShowDateIds);
        }
    }
}
