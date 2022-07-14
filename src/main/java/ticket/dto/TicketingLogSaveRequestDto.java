package ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketingLogSaveRequestDto {
    private Long userId;
    private Long seatId;

    @Builder
    public TicketingLogSaveRequestDto(Long userId, Long seatId){
        this.userId = userId;
        this.seatId = seatId;
    }
}
