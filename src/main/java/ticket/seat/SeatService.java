package ticket.seat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    //좌석이 이미 차지하는지 확인 메서드 필요

    //좌석 등록?
    public Long join(Seat seat){
        seatRepository.save(seat);
        return seat.getSeatId();
    }

    //좌석 찾기
    public Seat findOne(Long memberId){
        return seatRepository.findOne(memberId);
    }

    //좌석 전체 조회?
    public List<Seat> findSeats(){
        return seatRepository.findAll();
    }


}
