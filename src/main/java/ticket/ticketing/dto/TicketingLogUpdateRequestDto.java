package ticket.ticketing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketingLogUpdateRequestDto {
    private Long ticketingLogId;
    private boolean success;

    @Builder
    public TicketingLogUpdateRequestDto(Long id, boolean success) {
        this.ticketingLogId = id;
        this.success = success;
    }
}
