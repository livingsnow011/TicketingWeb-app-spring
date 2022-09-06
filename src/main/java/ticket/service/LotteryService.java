package ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.constant.BookStatus;
import ticket.entity.*;
import ticket.repository.BookRepository;
import ticket.repository.ShowDateRepository;
import ticket.repository.ShowSeatRepository;
import ticket.repository.TicketRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LotteryService {

    private final TicketRepository ticketRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowDateRepository showDateRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void doLottery(List<Long> showDateIds){
        for (Long showDateId : showDateIds){
            ShowDate showDate = showDateRepository.findById(showDateId).orElseThrow(EntityNotFoundException::new);
            showDate.checkIsBooked();

            List<Long> showSeatIds = ticketRepository.findDistinctShowSeatIdsByShowDateId(showDateId);

            for(Long showSeatId : showSeatIds){
                List<Long> ticketIdList = ticketRepository.findTicketsByShowDateAndShowSeat(showDateId, showSeatId);

                ShowSeat showSeat = showSeatRepository.findById(showSeatId).orElseThrow(EntityNotFoundException::new);
                if(showSeat.getSeatCount()>=ticketIdList.size()) {
                    //TODO
                    //좌석이 예매한 티켓 수보다 많거나 같으면
                    //모든 티켓을 예매처리
                    bookAllTicket(ticketIdList);
                }else{
                    //TODO
                    //좌석이 예매한 티켓수보다 적다면
                    //티켓들을 추첨을 통해, 성공과 환불을 결정한다.
                    bookLottery(ticketIdList,showSeat.getSeatCount(),showSeat.getPrice());
                }
            }
        }
    }

    public void bookAllTicket(List<Long> ticketIdList){
        for(Long ticketId : ticketIdList){
            Book book = ticketRepository.findBookById(ticketId).orElseThrow(EntityNotFoundException::new);
            book.changeBookStatus(BookStatus.BOOKED);
        }
    }

    public void bookLottery(List<Long> ticketIdList, Integer showSeatCount, Integer price){
        //Collection 셔플을 사용하여 ticket id를 섞음
        Collections.shuffle(ticketIdList);

        for (int i = 0; i<ticketIdList.size();++i){
            Book book = ticketRepository.findBookById(ticketIdList.get(i)).orElseThrow(EntityNotFoundException::new);
            if(i<showSeatCount){
                book.changeBookStatus(BookStatus.BOOKED);
            }else{
                book.changeBookStatus(BookStatus.CANCEL);
                //TODO 환불 수행
                User userScheduledForRefund = bookRepository.findUserById(book.getId());
                userScheduledForRefund.refundPoint(price);
            }
        }
    }
}
