package ticket.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ticket.entity.ShowImg;

@Getter
@Setter
public class ShowImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ShowImgDto of(ShowImg showImg){
        return modelMapper.map(showImg, ShowImgDto.class);
    }

}
