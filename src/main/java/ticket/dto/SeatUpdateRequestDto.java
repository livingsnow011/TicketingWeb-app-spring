package ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeatUpdateRequestDto {
    private Long id;
    private int addCount;

    public SeatUpdateRequestDto(Long id, int addCount) {
        this.id = id;
        this.addCount = addCount;
    }
}
