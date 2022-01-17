package ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ticket.service.LogService;
import ticket.ticketing.TicketingLog;
import ticket.ticketing.dto.TicketingLogSaveRequestDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LogController {
    private final LogService logService;

    @GetMapping("/log")
    public List<TicketingLog> findAll() {
        return logService.findAll();
    }

    @GetMapping("/log/{id}")
    public TicketingLog findById(@PathVariable Long id) {
        return logService.findById(id);
    }

    @PostMapping("/api/v1/log?{memberId}&{showInfoId}")
    public Long saveLog(@PathVariable(name = "memberId") Long memberId, @PathVariable(name = "showInfoId") Long showInfoId) {
        TicketingLogSaveRequestDto dto = TicketingLogSaveRequestDto.builder().memberId(memberId).showId(showInfoId).build();
        return logService.save(dto);
    }
}
