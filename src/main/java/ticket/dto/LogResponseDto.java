package ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ticket.entity.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LogResponseDto {
    private Long id;
    private ResponseUser user;
    private SeatResponseDto seat;
    private LocalDateTime createdTime;
    private boolean success;

    public LogResponseDto(TicketingLog log) {
        this.id = log.getId();
        this.user = new ResponseUser();
        user.setUserId(log.getUser().getUserId());
        user.setName(log.getUser().getName());
        user.setId(log.getUser().getId());
        this.seat = new SeatResponseDto(log.getSeat());
        this.createdTime = LocalDateTime.now();
        this.success = log.isSuccess();
    }
}
