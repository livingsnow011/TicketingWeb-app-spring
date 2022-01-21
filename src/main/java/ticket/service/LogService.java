package ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.entity.Seat;
import ticket.entity.TicketingLog;
import ticket.entity.UserEntity;
import ticket.repository.SeatRepository;
import ticket.repository.TicketingLogRepository;
import ticket.repository.UserRepository;
import ticket.dto.TicketingLogSaveRequestDto;
import ticket.dto.TicketingLogUpdateRequestDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final TicketingLogRepository ticketingLogRepository;

    @Transactional
    public String save(TicketingLogSaveRequestDto logDto) {
        UserEntity user = userRepository.findById(logDto.getUserId()).orElseThrow(() -> new NullPointerException("Cannot find user"));
        Seat seat = seatRepository.findById(logDto.getSeatId()).orElseThrow(() -> new NullPointerException("Cannot find seat"));

        TicketingLog newLog = TicketingLog.builder()
                .userId(user.getUserId())
                .seat(seat)
                .build();
        ticketingLogRepository.save(newLog);

        return "logging success";
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
    public List<TicketingLog> findBySeatId(Long id) {
        return ticketingLogRepository.findBySeatId(id);
    }

    @Transactional
    public List<TicketingLog> findByUserId(Long id) {
        return ticketingLogRepository.findByUserId(id);
    }

    @Transactional
    public List<TicketingLog> findLotteryTargetBySeatId(Long id) {
        return ticketingLogRepository.findLotteryTargetBySeatId(id);
    }
}
