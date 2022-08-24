package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ticket.constant.Classification;
import ticket.constant.ShowSellStatus;
import ticket.dto.ShowFormDto;

import javax.persistence.*;

@Table(name = "`show`")
@ToString
@Getter
@NoArgsConstructor
@Entity
public class Show{

    @Id
    @Column(name = "show_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,length = 50)
    private String showName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Classification classification;

    @Lob
    @Column(nullable = false)
    private String showDetail;

    @Enumerated(EnumType.STRING)
    private ShowSellStatus showSellStatus;


    @Builder
    public Show(String showName, Classification classification, String showDetail) {
        this.showName = showName;
        this.classification = classification;
        this.showDetail = showDetail;
    }

    public void updateShow(ShowFormDto showFormDto){
        this.showName = showFormDto.getShowName();
        this.classification = showFormDto.getClassification();
        this.showDetail = showFormDto.getShowDetail();
        this.showSellStatus = showFormDto.getShowSellStatus();
    }

}
