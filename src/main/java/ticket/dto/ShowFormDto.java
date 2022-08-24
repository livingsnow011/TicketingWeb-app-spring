package ticket.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import ticket.constant.Classification;
import ticket.constant.ShowSellStatus;
import ticket.entity.Show;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter     @Setter
public class ShowFormDto {

    private Long id;

    private Classification classification;

    private ShowSellStatus showSellStatus;

    @NotBlank(message = "공연 이름은 필수 입력 값입니다.")
    private String showName;

//    @NotNull(message = "가격은 필수 입력 값입니다.")

    @NotBlank(message = "공연 상세를 작성해 주세요")
    private String showDetail;


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private List<ShowDateDto> showDateDtoList = new ArrayList<>();
    private List<Long> showDateIds = new ArrayList<>();

    private List<ShowSeatDto> showSeatDtoList = new ArrayList<>();
    private List<Long> showSeatIds = new ArrayList<>();

    private List<ShowImgDto> showImgDtoList = new ArrayList<>();
    private List<Long> showImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Show createShow(){
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper.map(this, Show.class);
    }

    public void setShowFormDto(List<ShowDateDto> showDateDtoList,List<ShowSeatDto> showSeatDtoList,List<ShowImgDto> showImgDtoList){
        this.showDateDtoList = showDateDtoList;
        this.showSeatDtoList = showSeatDtoList;
        this.showImgDtoList = showImgDtoList;
    }

    public static ShowFormDto of(Show show){
        return modelMapper.map(show, ShowFormDto.class);
    }
}
