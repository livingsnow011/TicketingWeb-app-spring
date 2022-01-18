package ticket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ticket.entity.ShowInfo;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class ShowInfoResponseDto {
    private Long id;
    private String classification;
    private String name;
    private List<LocalDateTime> startDate;
    private List<SeatResponseDto> seatList;
    private String description;

    public ShowInfoResponseDto(ShowInfo showInfo) {
        this.id = showInfo.getId();
        this.classification = showInfo.getClassification();
        this.name = showInfo.getName();
        this.startDate = showInfo.getStartDate();
        this.description = showInfo.getDescription();
    }
}
