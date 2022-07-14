package ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticket.dto.LogResponseDto;
import ticket.dto.TicketingLogUpdateRequestDto;
import ticket.entity.TicketingLog;
import ticket.service.LogService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class LogController {
    private final LogService logService;

    @GetMapping("/log")
    public List<LogResponseDto> findAll() {
        return logService.findAll().stream().map(ticketingLog -> new LogResponseDto(ticketingLog)).collect(Collectors.toList());
    }

    @GetMapping("/log/{id}")
    public List<LogResponseDto> findByUserId(@PathVariable Long id) {
        List<LogResponseDto> responseDtoList = new ArrayList<>();
        List<TicketingLog> logList = logService.findByUserId(id);

        for (TicketingLog log : logList) {
            responseDtoList.add(new LogResponseDto(log));
        }

        return responseDtoList;
    }

    @PutMapping("/log/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestParam boolean success) {
        TicketingLogUpdateRequestDto requestDto = TicketingLogUpdateRequestDto.builder()
                .id(id).success(success).build();

        logService.update(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body("갱신 성공");
    }
}
