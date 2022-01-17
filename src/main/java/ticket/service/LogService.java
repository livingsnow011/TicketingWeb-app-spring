package ticket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ticket.ticketing.*;
import ticket.ticketing.dto.TicketingLogSaveRequestDto;
import ticket.ticketing.dto.TicketingLogUpdateRequestDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {
    private final MemberRepository memberRepository;
    private final ShowInfoRepository showInfoRepository;
    private final TicketingLogRepository ticketingLogRepository;

    @Transactional
    public Long save(TicketingLogSaveRequestDto logDto) {
        Member member = memberRepository.findById(logDto.getMemberId()).orElseThrow(() -> new NullPointerException("Cannot find member"));
        ShowInfo showInfo = showInfoRepository.findById(logDto.getShowId()).orElseThrow(() -> new NullPointerException("Cannot find show"));
        TicketingLog newLog = TicketingLog.builder()
                .member(member)
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
