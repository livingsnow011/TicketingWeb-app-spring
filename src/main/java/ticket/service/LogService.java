package ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.entity.Seat;
import ticket.entity.ShowInfo;
import ticket.entity.TicketingLog;
import ticket.entity.UserEntity;
import ticket.repository.SeatRepository;
import ticket.repository.ShowInfoRepository;
import ticket.repository.TicketingLogRepository;
import ticket.repository.UserRepository;
import ticket.dto.TicketingLogSaveRequestDto;
import ticket.dto.TicketingLogUpdateRequestDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {
    private final UserRepository userRepository;
    private final ShowInfoRepository showInfoRepository;
    private final SeatRepository seatRepository;
    private final TicketingLogRepository ticketingLogRepository;

    @Transactional
    public String save(TicketingLogSaveRequestDto logDto) {
        UserEntity user = userRepository.findById(logDto.getUserId()).orElseThrow(() -> new NullPointerException("Cannot find user"));
        Seat seat = seatRepository.findById(logDto.getSeatId()).orElseThrow(() -> new NullPointerException("Cannot find seat"));

        // 재화 보유량과 가격을 비교해서 결과에 따라 리턴
        if(seat.getPrice() <= user.getCurrent_point()){
            TicketingLog newLog = TicketingLog.builder()
                    .user(user)
                    .seat(seat)
                    .build();

            return "예매 성공";
        }else{
            return "재화가 부족합니다";
        }
    }

    @Transactional
    public Long update(TicketingLogUpdateRequestDto updateRequestDto) {
        TicketingLog log = ticketingLogRepository.findById(updateRequestDto.getTicketingLogId()).orElseThrow(() -> new NullPointerException("Cannot find log"));

        log.setSuccess(updateRequestDto.isSuccess());

        return updateRequestDto.getTicketingLogId();
    }

    @Transactional
    public void delete(Long id) {
        TicketingLog log = ticketingLogRepository.findById(id).orElseThrow(() -> new NullPointerException("Cannot find log"));

        ticketingLogRepository.delete(log);
    }

    @Transactional
    public List<TicketingLog> findAll() {
        return ticketingLogRepository.findAll();
    }

    @Transactional
    public TicketingLog findById(Long id) {
        return ticketingLogRepository.findById(id).orElseThrow(() -> new NullPointerException("Cannot find log id: " + id));
    }

    @Transactional
    public void makeReservation() {

    }
}
