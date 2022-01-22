package ticket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ticket.entity.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LogResponseDto {
    private Long id;
    private Long userId;
    private SeatResponseDto seat;
    private String showName;
    private LocalDateTime createdTime;
    private boolean success;

    public LogResponseDto(TicketingLog log) {
        this.id = log.getId();
        this.userId = log.getUserId();
        this.seat = new SeatResponseDto(log.getSeat());
        this.createdTime = LocalDateTime.now();
        this.success = log.isSuccess();
    }
}
