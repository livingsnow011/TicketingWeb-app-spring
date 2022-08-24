package ticket.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "book_show")
@Entity
public class BookShow {

    @Id
    @GeneratedValue
    @Column(name = "book_show_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int BookPrice;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    @Builder
    public BookShow(Show show,Book book){
        this.show = show;
        this.book = book;
    }
}
