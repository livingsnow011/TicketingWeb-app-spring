package ticket.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.entity.ShowDate;
import ticket.entity.Ticket;
import ticket.repository.TicketRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class LotteryService {

    private final TicketRepository ticketRepository;

    @Transactional
    public void doLottery(List<Long> showDateIds){
        List<Ticket> ticketList = new ArrayList<>();

        //3일 안에 상영 예정 공연들에 대한 티켓 리스트들
        for( Long showDateId : showDateIds){
            ticketList.addAll(ticketRepository.findTicketsByShowDateId(showDateId).orElseThrow(EntityNotFoundException::new));
        }

        List<Long> ticketsShowDateIds = new ArrayList<>();
        List<Long> ticketShowSeatIds = new ArrayList<>();


        for (int i = 0; i < ticketList.size(); i++) {

        }
    }

}
