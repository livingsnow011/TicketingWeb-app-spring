package ticket.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ShowInfoSaveRequestDto {
    private String name;
    private String classification;
    private String description;
    private String posterURI;
    private List<LocalDateTime> startDate;
    private List<SeatSaveRequestDto> seatSaveRequestDtoList;

    @Builder
    public ShowInfoSaveRequestDto(String name, String classification, String description, String posterURI, List<LocalDateTime> startDate, List<SeatSaveRequestDto> seatSaveRequestDtoList) {
        this.name = name;
        this.classification = classification;
        this.description = description;
        this.posterURI = posterURI;
        this.startDate = startDate;
        this.seatSaveRequestDtoList = seatSaveRequestDtoList;
    }
}
