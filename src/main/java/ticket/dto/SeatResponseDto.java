package ticket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ticket.entity.Seat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SeatResponseDto {
    private Long id;
    private Long showInfoId;
    private LocalDateTime showDate;
    private String grade;
    private int price;
    private int totalSeat;
    private int currentCount;

    public SeatResponseDto(Seat seat) {
        this.id = seat.getId();
        this.showInfoId = seat.getShowInfo().getId();
        this.showDate = seat.getShowDate();
        this.grade = seat.getGrade();
        this.price = seat.getPrice();
        this.totalSeat = seat.getTotalSeat();
        this.currentCount = seat.getCurrentCount();
    }
}
