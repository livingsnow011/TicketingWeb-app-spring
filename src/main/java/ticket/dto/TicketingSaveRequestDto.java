package ticket.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TicketingSaveRequestDto {
    private Long userId;
    private Long seatId;

    @Builder
    public TicketingSaveRequestDto(Long userId, Long seatId) {
        this.userId = userId;
        this.seatId = seatId;
    }
}
