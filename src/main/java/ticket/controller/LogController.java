package ticket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ticket.service.LogService;
import ticket.entity.TicketingLog;

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
}
