package ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import ticket.constant.Classification;
import ticket.constant.ShowSellStatus;
import ticket.entity.Show;


@Getter @Setter
@NoArgsConstructor
public class ShowGetDto{
    private Long id;

    private String showName;

    private Classification classification;

    private ShowSellStatus showSellStatus;

    private String imgUrl;


    @Builder
    public ShowGetDto(Long id,String showName, Classification classification, ShowSellStatus showSellStatus, String imgUrl) {
        this.id = id;
        this.showName = showName;
        this.classification = classification;
        this.showSellStatus = showSellStatus;
        this.imgUrl = imgUrl;
    }


    public Page<ShowGetDto> toDtoList(Page<Show> showList){
        Page<ShowGetDto> showGetDtoList = showList.map(show -> ShowGetDto.builder().
                id(show.getId()).
                classification(show.getClassification()).
                showSellStatus(show.getShowSellStatus()).
                build());

        return showGetDtoList;
    }
}
