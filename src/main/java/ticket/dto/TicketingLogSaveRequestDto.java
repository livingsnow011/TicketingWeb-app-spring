package ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketingLogSaveRequestDto {
    private Long userId;
    private Long seatId;
    private Long showId;

    @Builder
    public TicketingLogSaveRequestDto(Long userId, Long seatId, Long showId){
        this.userId = userId;
        this.seatId = seatId;
        this.showId = showId;
    }
}
