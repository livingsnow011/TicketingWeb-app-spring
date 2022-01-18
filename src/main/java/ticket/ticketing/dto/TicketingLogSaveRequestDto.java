package ticket.ticketing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketingLogSaveRequestDto {
    private Long userId;
    private Long showId;

    @Builder
    public TicketingLogSaveRequestDto(Long userId, Long showId){
        this.userId = userId;
        this.showId = showId;
    }
}
