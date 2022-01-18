package ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ShowInfoSaveRequestDto {
    private String name;
    private String classification;
    private String description;
    private List<LocalDateTime> startDate;
    private List<SeatSaveRequestDto> seatSaveRequestDtoList;

    @Builder
    public ShowInfoSaveRequestDto(String name, String classification, String description, List<LocalDateTime> startDate, List<SeatSaveRequestDto> seatSaveRequestDtoList) {
        this.name = name;
        this.classification = classification;
        this.description = description;
        this.startDate = startDate;
        this.seatSaveRequestDtoList = seatSaveRequestDtoList;
    }
}
