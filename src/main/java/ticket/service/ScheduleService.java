package ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ticket.entity.ShowDate;
import ticket.repository.ShowDateRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ShowDateRepository showDateRepository;
    private final LotteryService lotteryService;

    @Scheduled(cron = "10 * * * * ?")
    public void test(){
        LocalDateTime now = LocalDateTime.now();
        // 예매처리가 되지 않고, 지금보다 미래의 공연 날짜들을 모두 가져옴
        List<ShowDate> showDateList = showDateRepository.findShowDateByNow(now).orElseThrow(EntityNotFoundException::new);
        Iterator<ShowDate> iterator = showDateList.iterator();
        //메소드가 실행되고 3일 안까지 공연 날짜로 리스트를 줄임
        now = now.plusDays(3);

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
            if(showDate.getShowDate().isAfter(now)){
                iterator.remove();
            }else{
                targetShowDateIds.add(showDate.getId());
            }
        }

        if(!targetShowDateIds.isEmpty()){
            lotteryService.doLottery(targetShowDateIds);
        }
    }
}
