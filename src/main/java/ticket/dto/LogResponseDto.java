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
    private boolean refunded;

    public LogResponseDto(TicketingLog log) {
        this.id = log.getId();
        this.userId = log.getUserId();
        this.seat = new SeatResponseDto(log.getSeat());
        this.showName = log.getSeat().getShowName();
        this.createdTime = log.getCreatedTime();
        this.success = log.isSuccess();
        this.refunded = log.isRefunded();
    }
}
