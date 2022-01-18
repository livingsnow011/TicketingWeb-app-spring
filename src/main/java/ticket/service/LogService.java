package ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.entity.TicketingLog;
import ticket.entity.UserEntity;
import ticket.repository.TicketingLogRepository;
import ticket.repository.UserRepository;
import ticket.ticketing.*;
import ticket.ticketing.dto.TicketingLogSaveRequestDto;
import ticket.ticketing.dto.TicketingLogUpdateRequestDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {
    private final UserRepository userRepository;
    private final ShowInfoRepository showInfoRepository;
    private final TicketingLogRepository ticketingLogRepository;

    @Transactional
    public Long save(TicketingLogSaveRequestDto logDto) {
        UserEntity user = userRepository.findById(logDto.getUserId()).orElseThrow(() -> new NullPointerException("Cannot find user"));
        ShowInfo showInfo = showInfoRepository.findById(logDto.getShowId()).orElseThrow(() -> new NullPointerException("Cannot find show"));
        TicketingLog newLog = TicketingLog.builder()
                .user(user)
                .showInfo(showInfo)
                .build();

        return ticketingLogRepository.save(newLog).getId();
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
}
