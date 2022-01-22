package ticket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ticket.entity.ShowInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class ShowInfoResponseDto {
    private Long id;
    private String classification;
    private String name;
    private List<Long> seatIdList;
    private List<LocalDateTime> showDate;
    private String description;
    private String posterURI;

    public ShowInfoResponseDto(ShowInfo showInfo) {
        this.id = showInfo.getId();
        this.classification = showInfo.getClassification();
        this.name = showInfo.getName();
        this.seatIdList = new ArrayList<Long>(showInfo.getSeatList().stream().map((seat -> seat.getId())).collect(Collectors.toList()));
        this.description = showInfo.getDescription();
        this.posterURI = showInfo.getPosterURI();
    }
}
