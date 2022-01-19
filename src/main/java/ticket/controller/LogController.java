package ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticket.dto.LogResponseDto;
import ticket.dto.TicketingLogUpdateRequestDto;
import ticket.service.LogService;

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
    public LogResponseDto findById(@PathVariable Long id) {
        return new LogResponseDto(logService.findById(id));
    }

    @PutMapping("/log/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestParam boolean success) {
        TicketingLogUpdateRequestDto requestDto = TicketingLogUpdateRequestDto.builder()
                .id(id).success(success).build();

        logService.update(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body("갱신 성공");
    }

    @DeleteMapping("/log/{id}")
    public void delete(@PathVariable Long id) {
        // TODO 환불처리
        logService.delete(id);
    }
}
