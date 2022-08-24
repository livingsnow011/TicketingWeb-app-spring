package ticket.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import ticket.entity.ShowDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowDateDto {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime showDate;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ShowDateDto of(ShowDate showDate){
        return modelMapper.map(showDate, ShowDateDto.class);
    }
}
