package ticket.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ticket.entity.Ticket;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketDto {

    private String showName;

    private int price;

    private String grade;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime showDate;

    private String imgUrl;

    public TicketDto(Ticket ticket,String imgUrl){
        this.showName = ticket.getShowSeat().getShow().getShowName();
        this.price = ticket.getShowSeat().getPrice();
        this.grade = ticket.getShowSeat().getSeatGrade();
        this.showDate = ticket.getShowDate().getShowDate();
        this.imgUrl = imgUrl;
    }
}
