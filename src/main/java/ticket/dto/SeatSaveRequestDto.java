package ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeatSaveRequestDto {
    private String grade;
    private int price;
    private int totalSeat;

    @Builder
    public SeatSaveRequestDto(String grade, int price, int totalSeat) {
        this.grade = grade;
        this.price = price;
        this.totalSeat = totalSeat;
    }
}
