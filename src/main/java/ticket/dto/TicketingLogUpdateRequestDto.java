package ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketingLogUpdateRequestDto {
    private Long ticketingLogId;
    private boolean success;
    private boolean refunded;

    @Builder
    public TicketingLogUpdateRequestDto(Long id, boolean success, boolean refunded) {
        this.ticketingLogId = id;
        this.success = success;
        this.refunded = refunded;
    }
}
