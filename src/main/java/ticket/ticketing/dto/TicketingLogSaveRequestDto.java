package ticket.ticketing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketingLogSaveRequestDto {
    private Long memberId;
    private Long showId;

    @Builder
    public TicketingLogSaveRequestDto(Long memberId, Long showId){
        this.memberId = memberId;
        this.showId = showId;
    }
}
