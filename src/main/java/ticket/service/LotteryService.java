package ticket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.entity.ShowDate;

import java.util.List;

@Service
public class LotteryService {

    @Transactional
    public void doLottery(List<Long> showDateIds){

    }
}
