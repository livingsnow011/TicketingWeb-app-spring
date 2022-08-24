package ticket.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ticket.entity.ShowSeat;


@Getter
@Setter
public class ShowSeatDto {
    private Long id;

    private String seatGrade;

    private Integer seatCount;

    private Integer price;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ShowSeatDto of(ShowSeat showSeat){
        return modelMapper.map(showSeat, ShowSeatDto.class);
    }
}
