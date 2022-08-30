package ticket.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ticket.constant.BookStatus;
import ticket.entity.Book;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookHistDto {

    private Long bookId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime bookDate;
    private BookStatus bookStatus;

    private List<TicketDto> ticketDtoList = new ArrayList<>();

    public void addTicketDto(TicketDto ticketDto){
        ticketDtoList.add(ticketDto);
    }

    public BookHistDto(Book book){
        this.bookId = book.getId();
        this.bookDate = LocalDateTime.parse(book.getBookDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        this.bookStatus = book.getBookStatus();
    }

}
