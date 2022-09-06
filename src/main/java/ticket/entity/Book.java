package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ticket.constant.BookStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "book")
@Entity
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    private LocalDateTime bookDate;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @OneToOne(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Ticket ticket;

    public Long showId;


    //test 용 지워
    public void setUser(User user) {
        this.user = user;
    }

    @Builder
    Book(User user,LocalDateTime bookDate,BookStatus bookStatus,Ticket ticket,Long showId){
        this.user = user;
        this.bookDate = bookDate;
        this.bookStatus = bookStatus;
        this.ticket = ticket;
        this.showId = showId;
    }


    public void changeBookStatus(BookStatus bookStatus){
        this.bookStatus = bookStatus;
    }


}
