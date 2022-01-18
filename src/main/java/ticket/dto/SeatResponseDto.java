package ticket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ticket.entity.Seat;

@Data
@NoArgsConstructor
public class SeatResponseDto {
    private Long id;
    private ShowInfoResponseDto showInfo;
    private String grade;
    private int price;
    private int totalSeat;
    private int currentCount;

    public SeatResponseDto(Seat seat) {
        this.id = seat.getId();
        this.grade = seat.getGrade();
        this.price = seat.getPrice();
        this.totalSeat = seat.getTotalSeat();
        this.currentCount = seat.getCurrentCount();
    }
}
